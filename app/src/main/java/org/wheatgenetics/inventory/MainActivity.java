package org.wheatgenetics.inventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected final static String TAG = "Inventory";
    protected Settings ep;
    private UsbDevice mDevice;

    private String boxNumber;
    private EditText mWeightEditText;
    private TextView boxNumTextView;

    private EditText inputText;
    private TableLayout InventoryTable;
    private MySQLiteHelper db;
    private ScrollView sv1;
    private static int currentItemNum = 1;

    private LinearLayout parent;
    private ScrollView changeContainer;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Log.v(TAG, "onCreate()");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        {
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            toolbar.bringToFront();
        }

        {
            final ActionBar supportActionBar = getSupportActionBar();

            if (supportActionBar != null) {
                supportActionBar.setTitle(null);
                supportActionBar.getThemedContext();         // This appears to do nothing.  Remove?
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        {
            final NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
            nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
        }
        setupDrawer();

        sv1 = (ScrollView) findViewById(R.id.svData);
        mWeightEditText = (EditText) findViewById(R.id.etWeight);
        mWeightEditText.setText(getString(R.string.not_connected));
        boxNumTextView = (TextView) findViewById(R.id.tvBoxNum);
        boxNumTextView.setText("");
        inputText = (EditText) findViewById(R.id.etInput);
        InventoryTable = (TableLayout) findViewById(R.id.tlInventory);

        parent = new LinearLayout(this);
        changeContainer = new ScrollView(this);
        changeContainer.removeAllViews();
        changeContainer.addView(parent);

        db = new MySQLiteHelper(this);

        {
            final Button setBox = (Button) findViewById(R.id.btBox);
            setBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBoxDialog();
                }
            });
        }

        InventoryTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mDevice = getIntent().getParcelableExtra(UsbManager.EXTRA_DEVICE);

        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    {
                        final InputMethodManager mgr =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.showSoftInput(inputText, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return true;
                    addRecord(); // Add the current record to the table
                    goToBottom();
                    inputText.requestFocus(); // Set focus back to Enter box
                }

                if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        addRecord(); // Add the current record to the table
                        goToBottom();
                    }
                    inputText.requestFocus(); // Set focus back to Enter box
                }
                return false;
            }
        });

        mWeightEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    {
                        final InputMethodManager mgr =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.showSoftInput(inputText, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return true;
                    addRecord(); // Add the current record to the table
                    goToBottom();

                    if (mDevice != null) {
                        mWeightEditText.setText("");
                    }
                    inputText.requestFocus(); // Set focus back to Enter box
                }

                if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        addRecord(); // Add the current record to the table
                        goToBottom();
                    }
                    inputText.requestFocus(); // Set focus back to Enter box
                }
                return false;
            }
        });

        createDir(Constants.MAIN_PATH);
        parseDbToTable();
        goToBottom();

        ep = new Settings(getSharedPreferences("Settings", 0));

        if (!ep.firstNameIsSet()) {
            setPersonDialog();
        }

        if (!ep.getIgnoreScale()) {
            findScale();
        }

        int v = 0;
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Inventory", "" + e.getMessage());
        }
        if (!ep.updateVersionIsSet(v)) {
            ep.setUpdateVersion(v);
            changelog();
        }
    }

    private void goToBottom() {
        sv1.post(new Runnable() {
            @Override
            public void run() {
                sv1.fullScroll(ScrollView.FOCUS_DOWN);
                inputText.requestFocus();
            }
        });
    }

    private void parseDbToTable() {
        InventoryTable.removeAllViews();

        final List<InventoryRecord> inventoryRecords = db.getInventoryRecords();
        for (InventoryRecord inventoryRecord : inventoryRecords) {
            final String[] temp     = inventoryRecord.toString().split(",");
            final int      position = Integer.parseInt(temp[4]);
            Log.e(TAG, temp[0] + " " + position + " " + temp[1] + " " + temp[5]);
            createNewTableEntry(temp[0], position, temp[1], temp[5]);
            currentItemNum = position + 1;
        }
    }

    /**
     * Adds a new record to the internal list of records
     */
    private void addRecord() {
        {
            final String ut = inputText.getText().toString();
            if (ut.equals("")) {
                return; // check for empty user input
            }
        }

        final String boxID  = boxNumTextView.getText().toString() ;
        final String envID  = inputText.getText().toString()      ;
        final String weight = mWeightEditText.getText().toString();

        db.addInventoryRecord(new InventoryRecord(
            /* boxID    => */ boxID           ,
            /* envID    => */ envID           ,
            /* personID => */ ep.getSafeName(),
            /* date     => */ getDate()       ,
            /* position => */ currentItemNum  ,
            /* wt       => */ weight          )); // add to database

        createNewTableEntry(boxID, currentItemNum, envID, weight);
        currentItemNum++;
    }

    static private String getDate() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd-hh-mm-ss", Locale.getDefault());
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * Adds a new entry to the end of the TableView
     */
    private void createNewTableEntry(final String boxID,
    final int position, final String sampleID, final String sampleWeight) {
        inputText.setText("");

		/* Create a new row to be added. */
        final TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		/* Create the item number field. */
        final TextView itemNumTV = new TextView(this);
        itemNumTV.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        itemNumTV.setTextColor(Color.BLACK);
        itemNumTV.setTextSize(20.0f);
        itemNumTV.setText("" + position);
        itemNumTV.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.16f));

		/* Create the box number field. */
        final TextView boxNumTV = new TextView(this);
        boxNumTV.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        boxNumTV.setTextColor(Color.BLACK);
        boxNumTV.setTextSize(20.0f);
        boxNumTV.setText(boxID);
        boxNumTV.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.16f));

		/* Create the Envelope ID field. */
        final TextView envIDTV = new TextView(this);
        envIDTV.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        envIDTV.setTextColor(Color.BLACK);
        envIDTV.setTextSize(20.0f);
        envIDTV.setText(sampleID);
        envIDTV.setTag(boxID + "," + sampleID + "," + position);
        envIDTV.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f));
        envIDTV.setLongClickable(true);

		/* Define the listener for the longclick event. */
        envIDTV.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteDialog((String) v.getTag());
                return false;
            }
        });

		/* Create the Weight field. */
        final TextView weightTV = new TextView(this);
        weightTV.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        weightTV.setTextColor(Color.BLACK);
        weightTV.setTextSize(20.0f);
        weightTV.setText(sampleWeight);
        weightTV.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.16f));

		/* Add UI elements to row and add row to table. */
        tr.addView(itemNumTV);
        tr.addView(boxNumTV );
        tr.addView(envIDTV  );
        tr.addView(weightTV );
        InventoryTable.addView(tr, new LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
    }

    private void deleteDialog(final String tag) {
        final String tagArray[] = tag.split(",");
        final String box        = tagArray[0];
        final String env        = tagArray[1];
        final int    num        = Integer.parseInt(tagArray[2]);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.delete_entry));
        builder.setMessage(getString(R.string.delete) + env + "?")
               .setCancelable(true)
               .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteInventoryRecord(new InventoryRecord(box, env, num));
                        parseDbToTable();
                    }})
               .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }});
        builder.create().show();
    }

    private void createDir(final File path) {
        final File blankFile = new File(path, ".inventory");

        if (!path.exists()) {
            path.mkdirs();

            try {
                blankFile.getParentFile().mkdirs();
                blankFile.createNewFile();
                makeFileDiscoverable(blankFile, this);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void setBoxDialog() {
        final EditText input = new EditText(this);
        input.setText(boxNumber);
        input.selectAll();
        input.setSingleLine();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.setbox));
        alert.setView(input);
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                {
                    final String value = input.getText().toString().trim();

                    boxNumTextView.setText(value);
                    boxNumber = value;
                }

                final InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }});

        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                final InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
            });
        alert.create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return true;
    }

    private void aboutDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        {
            final View personView =
                this.getLayoutInflater().inflate(R.layout.about, new LinearLayout(this), false);

            {
                final TextView version = (TextView) personView.findViewById(R.id.tvVersion);
                try {
                    final PackageInfo packageInfo =
                        this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
                    version.setText(getResources().getString(R.string.versiontitle) + " " + packageInfo.versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, e.getMessage());
                }
                version.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changelog();
                    }});
            }

            {
                final TextView otherApps = (TextView) personView.findViewById(R.id.tvOtherApps);
                otherApps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showOtherAppsDialog();
                    }});
            }

            alert.setCancelable(true);
            alert.setTitle(getResources().getString(R.string.about));
            alert.setView(personView);
        }
        alert.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }});
        alert.show();
    }

    protected void makeToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setPersonDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.set_person));
        {
            final View personView =
                this.getLayoutInflater().inflate(R.layout.person, new LinearLayout(this), false);

            final EditText fName = (EditText) personView.findViewById(R.id.firstName);
            final EditText lName = (EditText) personView.findViewById(R.id.lastName );

            fName.setText(ep.getFirstName());
            lName.setText(ep.getLastName() );

            builder.setView(personView);
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String firstName = fName.getText().toString().trim();
                    final String lastName  = lName.getText().toString().trim();

                    if (firstName.length() == 0 | lastName.length() == 0) {
                        makeToast(getResources().getString(R.string.no_blank));
                        setPersonDialog();
                        return;
                    }

                    makeToast(getResources().getString(R.string.person_set) + " " + firstName + " " + lastName);
                    ep.setName(firstName, lastName);
                }});
        }
        builder.show();
    }

    private void showOtherAppsDialog() {
        final AlertDialog.Builder otherAppsAlert = new AlertDialog.Builder(this);
        {
            final ListView myList = new ListView(this);

            myList.setDivider(null);
            myList.setDividerHeight(0);
            {
                final String[] links = {                                   //TODO update these links
                    "https://play.google.com/store/apps/details?id=com.fieldbook.tracker",
                    "http://wheatgenetics.org/apps"                                      ,
                    "http://wheatgenetics.org/apps"                                      };
                myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                    View view, int position, long id) {
                        switch (position) {
                            case 0:
                            case 1:
                            case 2:
                                startActivity(new Intent(
                                    Intent.ACTION_VIEW, Uri.parse(links[position])));
                                break;
                        }
                    }});
            }
            {
                final Integer appIconIDs[] = {R.drawable.other_ic_field_book,
                    R.drawable.other_ic_coordinate, R.drawable.other_ic_1kk};

                final String[] appNames = new String[3];
                appNames[0] = "Field Book";
                appNames[1] = "Coordinate";
                appNames[2] = "1KK"       ;
                //appNames[3] = "Intercross";
                //appNames[4] = "Rangle"    ;

                myList.setAdapter(new CustomListAdapter(this, appIconIDs, appNames));
            }

            otherAppsAlert.setCancelable(true);
            otherAppsAlert.setTitle(getResources().getString(R.string.otherapps));
            otherAppsAlert.setView(myList);
        }
        otherAppsAlert.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }});
        otherAppsAlert.show();
    }

    public class CustomListAdapter extends ArrayAdapter<String> {
        protected Context   context    ;
        protected String[]  color_names;
        protected Integer[] image_ids  ;

        public CustomListAdapter(final Activity context,
        final Integer[] image_ids, final String[] color_names) {
            super(context, R.layout.appline, color_names);

            this.context     = context    ;
            this.color_names = color_names;
            this.image_ids   = image_ids  ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View single_row = inflater.inflate(R.layout.appline, null, true);
            {
                final TextView textView = (TextView) single_row.findViewById(R.id.txt);
                textView.setText(color_names[position]);
            }
            {
                final ImageView imageView = (ImageView) single_row.findViewById(R.id.img);
                imageView.setImageResource(image_ids[position]);
            }
            return single_row;
        }
    }

    private void clearDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(
            getString(R.string.delete_msg_1))
            .setCancelable(false)
            .setTitle(getString(R.string.clear_data))
            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boxNumTextView.setText("");
                    makeToast(getString(R.string.data_deleted));
                    dropTables();
                }})
            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }});
        builder.create().show();
    }

    private void export() {
        new AlertDialog.Builder(MainActivity.this)
            .setTitle(getString(R.string.export_data))
            .setMessage(getString(R.string.export_choice))
            .setPositiveButton(getString(R.string.export_csv), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    exportCSV();
                }})
            .setNegativeButton(getString(R.string.export_sql), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    exportSQL();
                }})
            .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }}).show();
    }

    private void exportCSV() {
        try {
            writeCSV("inventory_" + getDate() + ".csv");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dropTables();
    }

    private void exportSQL() {
        try {
            writeSQL("inventory_" + getDate() + ".sql");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dropTables();
    }

    private void writeCSV(final String filename) {
        final List<InventoryRecord> inventoryRecords = db.getInventoryRecords();
        if (inventoryRecords.size() > 0) {
            try
            {
                {
                    final File myFile = new File(Constants.MAIN_PATH, filename);
                    myFile.createNewFile();
                    {
                        final FileOutputStream fOut = new FileOutputStream(myFile);
                        {
                            final OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                            String record =
                                "box_id,seed_id,inventory_date,inventory_person,weight_gram\r\n";
                            myOutWriter.append(record);

                            for (InventoryRecord inventoryRecord : inventoryRecords) {
                                {
                                    final String[] temp = inventoryRecord.toString().split(",");

                                    record =  temp[0] + ","   ; // box
                                    record += temp[1] + ","   ; // seed id
                                    record += temp[3] + ","   ; // date
                                    record += temp[2] + ","   ; // person
                                    record += temp[5] + "\r\n"; // weight
                                }
                                myOutWriter.append(record);
                            }
                            myOutWriter.close();
                        }
                        fOut.close();
                    }
                    makeFileDiscoverable(myFile, this);
                }
                makeToast("File exported successfully.");
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
        shareFile(filename);
        dropTables();
    }

    private void writeSQL(final String filename) {
        final List<InventoryRecord> inventoryRecords = db.getInventoryRecords()     ;
        final int                   itemCount        = inventoryRecords.size();
        if (itemCount > 0) {
            try
            {
                {
                    final File myFile = new File(Constants.MAIN_PATH, filename);
                    myFile.createNewFile();
                    {
                        final FileOutputStream fOut = new FileOutputStream(myFile);
                        {
                            final OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                            {
                                String record = "DELETE FROM seedinv WHERE seedinv.box_id in (" +
                                    db.getBoxList() + ");\n";

                                record += "INSERT INTO seedinv(`box_id`,`seed_id`," +
                                    "`inventory_date`,`inventory_person`,`weight_gram`)\r\nVALUES";
                                myOutWriter.append(record);
                            }
                            for (int i = 0; i < itemCount; i++) {
                                String record = "(";
                                {
                                    final String[] temp =
                                        inventoryRecords.get(i).toString().split(",");
                                    for (int j = 0; j < temp.length; j++) {
                                        if (temp[j].length() == 0) {
                                            temp[j] = "null";
                                        }
                                    }

                                    record += addTicks(temp[0]) + ","; // box
                                    record += addTicks(temp[1]) + ","; // seed id
                                    record += addTicks(temp[3]) + ","; // date
                                    record += addTicks(temp[2]) + ","; // person
                                    record += addTicks(temp[5])      ; // weight
                                }
                                record += ")";

                                if (i == itemCount - 1) {
                                    record += ";\r\n";
                                } else {
                                    record += ",\r\n";
                                }

                                myOutWriter.append(record);
                            }
                            myOutWriter.close();
                        }
                        fOut.close();
                    }
                    makeFileDiscoverable(myFile, this);
                }
                makeToast(getString(R.string.export_success));
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
        shareFile(filename);
        dropTables();
    }

    static protected void makeFileDiscoverable(final File file, final Context context) {
        MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, null, null);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
            Uri.fromFile(file)));
    }

    static private String addTicks(final String entry) {
        return entry.contains("null") ? "null" : "'" + entry + "'";
    }

    private void dropTables() {
        db.deleteInventoryRecords();
        InventoryTable.removeAllViews();
        currentItemNum = 1;
    }

    private void shareFile(String filePath) {
        filePath = Constants.MAIN_PATH.toString() + filePath;

        final Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath));
        startActivity(Intent.createChooser(intent, getString(R.string.sending_file)));
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,
        mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                final TextView person = (TextView) findViewById(R.id.nameLabel);
                person.setText(ep.getName());
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }};

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    protected void selectDrawerItem(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.scaleConnect:
                findScale();
                break;
            case R.id.person:
                setPersonDialog();
                break;
            case R.id.export:
                export();
                break;
            case R.id.clearData:
                clearDialog();
                break;
            case R.id.about:
                aboutDialog();
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    private void changelog() {
        parent.setOrientation(LinearLayout.VERTICAL);
        parseLog(R.raw.changelog_releases);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.updatemsg));
        builder.setView(changeContainer)
               .setCancelable(true)
               .setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }});
        builder.create().show();
    }

    protected void parseLog(final int resId) {
        try {
            final InputStream       is  = getResources().openRawResource(resId);
            final InputStreamReader isr = new InputStreamReader(is)            ;
            final BufferedReader    br  = new BufferedReader(isr, 8192)        ;

            final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(20, 5, 20, 0);

            String curVersionName = null;
            String line;

            while ((line = br.readLine()) != null) {
                final TextView header  = new TextView(this);
                final TextView content = new TextView(this);
                final TextView spacer  = new TextView(this);
                final View     ruler   = new View    (this);

                header.setLayoutParams (lp);
                content.setLayoutParams(lp);
                spacer.setLayoutParams (lp);
                ruler.setLayoutParams  (lp);

                spacer.setTextSize(5);

                ruler.setBackgroundColor(getResources().getColor(R.color.main_colorAccent));
                header.setTextAppearance (getApplicationContext(), R.style.ChangelogTitles );
                content.setTextAppearance(getApplicationContext(), R.style.ChangelogContent);

                if (line.length() == 0) {
                    curVersionName = null;
                    spacer.setText("\n");
                    parent.addView(spacer);
                } else if (curVersionName == null) {
                    {
                        final String[] lineSplit = line.split("/");
                        curVersionName = lineSplit[1];
                    }
                    header.setText(curVersionName);
                    parent.addView(header);
                    parent.addView(ruler);
                } else {
                    content.setText("•  " + line);
                    parent.addView(content);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    protected void findScale() {
        if (mDevice == null) {
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            final HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            for (UsbDevice usbDevice : deviceList.values()) {
                mDevice = usbDevice;
                Log.v(TAG, String.format("name=%s deviceId=%d productId=%d " +
                    "vendorId=%d deviceClass=%d subClass=%d protocol=%d interfaceCount=%d",
                    mDevice.getDeviceName()    , mDevice.getDeviceId()      ,
                    mDevice.getProductId()     , mDevice.getVendorId()      ,
                    mDevice.getDeviceClass()   , mDevice.getDeviceSubclass(),
                    mDevice.getDeviceProtocol(), mDevice.getInterfaceCount()));
                break;
            }
        }

        if (mDevice != null) {
            mWeightEditText.setText("0");
            new ScaleListener().execute();
        } else {
            new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.no_scale))
                .setMessage(getString(R.string.connect_scale))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.try_again),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            findScale();
                        }})
                .setNegativeButton(getString(R.string.ignore),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ep.setIgnoreScaleToTrue();
                            dialog.cancel();
                        }}).show();
        }
    }

    private class ScaleListener extends AsyncTask<Void, Double, Void> {
        private double mLastWeight = 0;

        @Override
        protected Void doInBackground(Void... params) {
            Log.v(TAG, "start transfer");

            if (mDevice == null) {
                Log.e(TAG, "no device");
                return null;
            }
            final UsbInterface intf = mDevice.getInterface(0);
            Log.v(TAG, String.format("endpoint count = %d", intf.getEndpointCount()));
            final UsbEndpoint endpoint = intf.getEndpoint(0);
            Log.v(TAG, String.format("endpoint direction = %d out = %d in = %d",
                endpoint.getDirection(), UsbConstants.USB_DIR_OUT, UsbConstants.USB_DIR_IN));
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            final UsbDeviceConnection connection = usbManager.openDevice(mDevice);
            connection.claimInterface(intf, true);
            final byte[] data = new byte[128];
            while (true) {
                final int length = connection.bulkTransfer(endpoint,
                    data, data.length, /* timeout => */ 2000);

                if (length != 6) {
                    Log.e(TAG, String.format("invalid length: %d", length));
                    return null;
                }

                final byte report = data[0];
                final byte status = data[1];
                //    byte exp    = data[3];
                final short weightLSB = (short) (data[4] & 0xff);
                final short weightMSB = (short) (data[5] & 0xff);

                // Log.v(TAG, String.format(
                // "report=%x status=%x exp=%x lsb=%x msb=%x", report,
                // status, exp, weightLSB, weightMSB));

                if (report != 3) {
                    Log.v(TAG, String.format("scale status error %d", status));
                    return null;
                }

                double mWeightGrams = weightLSB + weightMSB * 256.0;
                if (mDevice.getProductId() == 519) {
                    mWeightGrams /= 10.0;
                }
                final double mZeroGrams = 0;
                final double zWeight = mWeightGrams - mZeroGrams;

                switch (status) {
                    case 1:
                        Log.w(TAG, "Scale reports FAULT!\n");
                        break;
                    case 3:
                        Log.i(TAG, "Weighing...");
                        if (mLastWeight != zWeight) {
                            publishProgress(zWeight);
                        }
                        break;
                    case 2:
                    case 4:
                        if (mLastWeight != zWeight) {
                            Log.i(TAG, String.format("Final Weight: %f", zWeight));
                            publishProgress(zWeight);
                        }
                        break;
                    case 5:
                        Log.w(TAG, "Scale reports Under Zero");
                        if (mLastWeight != zWeight) {
                            publishProgress(0.0);
                        }
                        break;
                    case 6:
                        Log.w(TAG, "Scale reports Over Weight!");
                        break;
                    case 7:
                        Log.e(TAG, "Scale reports Calibration Needed!");
                        break;
                    case 8:
                        Log.e(TAG, "Scale reports Re-zeroing Needed!\n");
                        break;
                    default:
                        Log.e(TAG, "Unknown status code");
                        break;
                }

                mLastWeight = zWeight;
            }
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            Log.i(TAG, "update progress");

            final String weightText = String.format("%.1f", values[0]);
            Log.i(TAG, weightText);
            mWeightEditText.setText(weightText);
            mWeightEditText.invalidate();
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(),
                getString(R.string.scale_disconnect), Toast.LENGTH_LONG).show();
            mDevice = null;
            mWeightEditText.setText(getString(R.string.not_connected));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}