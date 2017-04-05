package org.wheatgenetics.inventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

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
    private final static String TAG = "Inventory";

    private static int currentItemNum = 1;


    // region Instance Fields
    // region Widget Instance Fields
    private android.support.v4.widget.DrawerLayout       drawerLayout         ;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.TextView boxTextView  ;
    private android.widget.EditText envidEditText;
    private android.widget.EditText wtEditText   ;

    private android.widget.TableLayout tableLayout;
    private android.widget.ScrollView  scrollView ;

    private android.widget.LinearLayout parent;
    private android.widget.ScrollView changeContainer;
    // endregion


    protected org.wheatgenetics.inventory.SharedPreferences sharedPreferences;
    private   UsbDevice                                     usbDevice        ;

    private String       box         ;
    private SamplesTable samplesTable;
    // endregion


    // region Class Methods
    // region Log Class Methods
    static private int sendVerboseLogMsg(final java.lang.String msg)
    {
        return android.util.Log.v(org.wheatgenetics.inventory.MainActivity.TAG, msg);
    }

    static private int sendInfoLogMsg(final java.lang.String msg)
    {
        return android.util.Log.i(org.wheatgenetics.inventory.MainActivity.TAG, msg);
    }

    static private int sendWarnLogMsg(final java.lang.String msg)
    {
        return android.util.Log.w(org.wheatgenetics.inventory.MainActivity.TAG, msg);
    }

    static private int sendErrorLogMsg(final java.lang.String msg)
    {
        return android.util.Log.e(org.wheatgenetics.inventory.MainActivity.TAG, msg);
    }

    static private int sendErrorLogMsg(final java.lang.Exception exception)
    {
        return org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(exception.getMessage());
    }
    // endregion


    // region Toast Class Methods
    static private void showToast(final android.content.Context context,
    final java.lang.CharSequence text, final int duration)
    {
        android.widget.Toast.makeText(context, text, duration).show();
    }

    static private void showToast(
    final android.content.Context context, final java.lang.CharSequence text)
    {
        org.wheatgenetics.inventory.MainActivity.showToast(
            context, text, android.widget.Toast.LENGTH_SHORT);
    }

    protected void showToast(final java.lang.CharSequence text)
    {
        org.wheatgenetics.inventory.MainActivity.showToast(this, text);
    }
    // endregion
    // endregion


    // region Overridden Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_layout);
        org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg("onCreate()");

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        {
            final Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);

            this.setSupportActionBar(toolbar);
            toolbar.bringToFront();
        }

        {
            final ActionBar supportActionBar = this.getSupportActionBar();

            if (supportActionBar != null) {
                supportActionBar.setTitle(null);
                supportActionBar.getThemedContext();         // This appears to do nothing.  Remove?
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled(true);
            }
        }

        this.drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        {
            final NavigationView navigationView = (NavigationView) this.findViewById(R.id.nvView);
            navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectNavigationItem(item);
                        return true;
                    }});
        }
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this,
            this.drawerLayout, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    final TextView personTextView = (TextView) findViewById(R.id.nameLabel);
                    personTextView.setText(sharedPreferences.getName());
                }

                @Override
                public void onDrawerClosed(View drawerView) {}
            };
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);

        this.boxTextView = (TextView) this.findViewById(R.id.tvBoxNum);
        this.boxTextView.setText("");

        this.envidEditText = (EditText) this.findViewById(R.id.etInput);

        this.wtEditText = (EditText) this.findViewById(R.id.etWeight);
        this.wtEditText.setText(getString(R.string.not_connected));

        this.tableLayout = (TableLayout) this.findViewById(R.id.tlInventory);
        this.scrollView  = (ScrollView ) this.findViewById(R.id.svData     );

        parent = new LinearLayout(this);
        changeContainer = new ScrollView(this);
        changeContainer.removeAllViews();
        changeContainer.addView(parent);

        this.samplesTable = new SamplesTable(this);

        {
            final Button setBoxButton = (Button) this.findViewById(R.id.btBox);
            setBoxButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { setBox(); }});
        }

        this.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}});

        this.usbDevice = this.getIntent().getParcelableExtra(UsbManager.EXTRA_DEVICE);

        this.envidEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    {
                        final InputMethodManager mgr =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.showSoftInput(envidEditText, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                    if (event.getAction() != KeyEvent.ACTION_DOWN) return true;
                    addRecord(); // Add the current record to the table
                    goToBottom();
                    envidEditText.requestFocus(); // Set focus back to Enter box
                }

                if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) return true;
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        addRecord(); // Add the current record to the table
                        goToBottom();
                    }
                    envidEditText.requestFocus(); // Set focus back to Enter box
                }
                return false;
            }});

        this.wtEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    {
                        final InputMethodManager mgr =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.showSoftInput(envidEditText, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                    if (event.getAction() != KeyEvent.ACTION_DOWN) return true;
                    addRecord(); // Add the current record to the table
                    goToBottom();

                    if (usbDevice != null) wtEditText.setText("");
                    envidEditText.requestFocus(); // Set focus back to Enter box
                }

                if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) return true;
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        addRecord(); // Add the current record to the table
                        goToBottom();
                    }
                    envidEditText.requestFocus(); // Set focus back to Enter box
                }
                return false;
            }});

        try { this.makeFileDiscoverable(InventoryDir.createIfMissing()); }
        catch (IOException e) {
            org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(e);
        }
        this.parseDbToTable();
        this.goToBottom();

        sharedPreferences =
            new org.wheatgenetics.inventory.SharedPreferences(getSharedPreferences("Settings", 0));

        if (!sharedPreferences.firstNameIsSet()) this.setPersonDialog();
        if (!sharedPreferences.getIgnoreScale()) this.findScale()      ;

        int v = 0;
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(e);
        }
        if (!sharedPreferences.updateVersionIsSet(v)) {
            sharedPreferences.setUpdateVersion(v);
            this.changelog();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg("onStart()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.actionBarDrawerToggle.onOptionsItemSelected(item)) return true;

        switch (item.getItemId()) {
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    // endregion


    private void goToBottom() {
        this.scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                envidEditText.requestFocus();
            }
        });
    }

    private void parseDbToTable() {
        this.tableLayout.removeAllViews();

        final Iterator<InventoryRecord> iterator = this.samplesTable.getAll().iterator();
        this.samplesTable.close();
        while (iterator.hasNext()) {
            final InventoryRecord inventoryRecord = iterator.next();
            inventoryRecord.sendErrorLogMsg(org.wheatgenetics.inventory.MainActivity.TAG);

            final int position = inventoryRecord.getPosition();
            createNewTableEntry(inventoryRecord.getBox(), position,
                inventoryRecord.getEnvId(), inventoryRecord.getWt());
            currentItemNum = position + 1;
        }
    }

    /**
     * Adds a new record to the internal list of records
     */
    private void addRecord() {
        {
            final String envid = this.envidEditText.getText().toString();
            if (envid.equals("")) {
                return; // check for empty user input
            }
        }

        final String box   = this.boxTextView.getText().toString()  ;
        final String envid = this.envidEditText.getText().toString();
        final String wt    = this.wtEditText.getText().toString()   ;

        this.samplesTable.add(new InventoryRecord(
            /* box      => */ box                            ,
            /* envid    => */ envid                          ,
            /* person   => */ sharedPreferences.getSafeName(),
            /* position => */ currentItemNum                 ,
            /* wt       => */ wt                             ));

        createNewTableEntry(box, currentItemNum++, envid, wt);
    }

    /**
     * Adds a new entry to the end of the TableView
     */
    private void createNewTableEntry(final String box,
    final int position, final String sampleID, final String sampleWeight) {
        this.envidEditText.setText("");

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
        boxNumTV.setText(box);
        boxNumTV.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.16f));

		/* Create the Envelope ID field. */
        final TextView envIDTV = new TextView(this);
        envIDTV.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        envIDTV.setTextColor(Color.BLACK);
        envIDTV.setTextSize(20.0f);
        envIDTV.setText(sampleID);
        envIDTV.setTag(box + "," + sampleID + "," + position);
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
        this.tableLayout.addView(tr, new LayoutParams(
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
                        samplesTable.delete(new InventoryRecord(box, env, num));
                        parseDbToTable();
                    }})
               .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }});
        builder.create().show();
    }

    private void setBox() {
        final EditText input = new EditText(this);
        input.setText(this.box);
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

                    boxTextView.setText(value);
                    box = value;
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
                    org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(e);
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

    private void setPersonDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.set_person));
        {
            final View personView =
                this.getLayoutInflater().inflate(R.layout.person, new LinearLayout(this), false);

            final EditText fName = (EditText) personView.findViewById(R.id.firstName);
            final EditText lName = (EditText) personView.findViewById(R.id.lastName );

            fName.setText(sharedPreferences.getFirstName());
            lName.setText(sharedPreferences.getLastName() );

            builder.setView(personView);
            builder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String firstName = fName.getText().toString().trim();
                        final String lastName  = lName.getText().toString().trim();

                        if (firstName.length() == 0 | lastName.length() == 0) {
                            showToast(getResources().getString(R.string.no_blank));
                            setPersonDialog();
                            return;
                        }

                        showToast(getResources().getString(R.string.person_set) +
                            " " + firstName + " " + lastName);
                        sharedPreferences.setName(firstName, lastName);
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
        otherAppsAlert.setNegativeButton(getResources().getString(R.string.ok),
            new DialogInterface.OnClickListener() {
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
                    boxTextView.setText("");
                    showToast(getString(R.string.data_deleted));
                    deleteAll();
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
            .setPositiveButton(getString(R.string.export_csv),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { exportCSV(); }})
            .setNegativeButton(getString(R.string.export_sql),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { exportSQL(); }})
            .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }})
            .show();
    }

    private void exportCSV() {
        {
            final InventoryRecords inventoryRecords = this.samplesTable.getAll();
            this.samplesTable.close();
            {
                final String fileName = Utils.getExportFileName() + ".csv";
                try { shareFile(inventoryRecords.writeCSV(fileName), fileName); }
                catch (IOException e) {
                    org.wheatgenetics.inventory.MainActivity.showToast(
                        this.getBaseContext(), e.getMessage());
                }
            }
        }
        deleteAll();
    }

    private void exportSQL() {
        {
            final InventoryRecords inventoryRecords = this.samplesTable.getAll()    ;
            final String           boxList          = this.samplesTable.getBoxList();
            this.samplesTable.close();
            {
                final String fileName = Utils.getExportFileName() + ".sql";
                try { shareFile(inventoryRecords.writeSQL(fileName, boxList), fileName); }
                catch (IOException e) {
                    org.wheatgenetics.inventory.MainActivity.showToast(
                        this.getBaseContext(), e.getMessage());
                }
            }
        }
        deleteAll();
    }

    protected void makeFileDiscoverable(final File file) {
        if (file != null)
        {
            MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, null, null);
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
        }
    }

    private void deleteAll() {
        this.samplesTable.deleteAll();
        this.tableLayout.removeAllViews();
        currentItemNum = 1;
    }

    private void shareFile(final File file, final String fileName) {
        makeFileDiscoverable(file);
        showToast(getString(R.string.export_success));

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        intent.setType ("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, InventoryDir.parse(fileName));

        startActivity(Intent.createChooser(intent, getString(R.string.sending_file)));
    }

    protected void selectNavigationItem(final MenuItem menuItem) {
        assert menuItem != null;
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
        this.drawerLayout.closeDrawers();
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

    protected void findScale() {
        if (this.usbDevice == null) {
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            final HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            for (UsbDevice usbDevice : deviceList.values()) {
                this.usbDevice = usbDevice;
                org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(String.format(
                    "name=%s deviceId=%d productId=%d vendorId=%d " +
                    "deviceClass=%d subClass=%d protocol=%d interfaceCount=%d",
                    this.usbDevice.getDeviceName()    , this.usbDevice.getDeviceId()      ,
                    this.usbDevice.getProductId()     , this.usbDevice.getVendorId()      ,
                    this.usbDevice.getDeviceClass()   , this.usbDevice.getDeviceSubclass(),
                    this.usbDevice.getDeviceProtocol(), this.usbDevice.getInterfaceCount()));
                break;
            }
        }

        if (this.usbDevice != null) {
            this.wtEditText.setText("0");
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
                            sharedPreferences.setIgnoreScaleToTrue();
                            dialog.cancel();
                        }}).show();
        }
    }

    private class ScaleListener extends AsyncTask<Void, Double, Void> {
        private double mLastWeight = 0;

        @Override
        protected Void doInBackground(Void... params) {
            org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg("start transfer");

            if (usbDevice == null) {
                org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg("no device");
                return null;
            }
            final UsbInterface intf = usbDevice.getInterface(0);
            org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(
                String.format("endpoint count = %d", intf.getEndpointCount()));
            final UsbEndpoint endpoint = intf.getEndpoint(0);
            org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(
                String.format("endpoint direction = %d out = %d in = %d",
                endpoint.getDirection(), UsbConstants.USB_DIR_OUT, UsbConstants.USB_DIR_IN));
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            final UsbDeviceConnection connection = usbManager.openDevice(usbDevice);
            connection.claimInterface(intf, true);
            final byte[] data = new byte[128];
            while (true) {
                final int length = connection.bulkTransfer(endpoint,
                    data, data.length, /* timeout => */ 2000);

                if (length != 6) {
                    org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(
                        String.format("invalid length: %d", length));
                    return null;
                }

                final byte report = data[0];
                final byte status = data[1];
                //    byte exp    = data[3];
                final short weightLSB = (short) (data[4] & 0xff);
                final short weightMSB = (short) (data[5] & 0xff);

                // org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(String.format(
                // "report=%x status=%x exp=%x lsb=%x msb=%x", report,
                // status, exp, weightLSB, weightMSB));

                if (report != 3) {
                    org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(
                        String.format("scale status error %d", status));
                    return null;
                }

                double mWeightGrams = weightLSB + weightMSB * 256.0;
                if (usbDevice.getProductId() == 519) mWeightGrams /= 10.0;
                final double mZeroGrams = 0;
                final double zWeight = mWeightGrams - mZeroGrams;

                switch (status) {
                    case 1:
                        org.wheatgenetics.inventory.MainActivity.sendWarnLogMsg(
                            "Scale reports FAULT!\n");
                        break;
                    case 3:
                        org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg("Weighing...");
                        if (mLastWeight != zWeight) {
                            publishProgress(zWeight);
                        }
                        break;
                    case 2:
                    case 4:
                        if (mLastWeight != zWeight) {
                            org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg(
                                String.format("Final Weight: %f", zWeight));
                            publishProgress(zWeight);
                        }
                        break;
                    case 5:
                        org.wheatgenetics.inventory.MainActivity.sendWarnLogMsg(
                            "Scale reports Under Zero");
                        if (mLastWeight != zWeight) {
                            publishProgress(0.0);
                        }
                        break;
                    case 6:
                        org.wheatgenetics.inventory.MainActivity.sendWarnLogMsg(
                            "Scale reports Over Weight!");
                        break;
                    case 7:
                        org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(
                            "Scale reports Calibration Needed!");
                        break;
                    case 8:
                        org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(
                            "Scale reports Re-zeroing Needed!\n");
                        break;
                    default:
                        org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(
                            "Unknown status code");
                        break;
                }

                mLastWeight = zWeight;
            }
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg("update progress");

            final String weightText = String.format("%.1f", values[0]);
            org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg(weightText);
            wtEditText.setText(weightText);
            wtEditText.invalidate();
        }

        @Override
        protected void onPostExecute(Void result) {
            org.wheatgenetics.inventory.MainActivity.showToast(getApplicationContext(),
                getString(R.string.scale_disconnect), Toast.LENGTH_LONG);
            usbDevice = null;
            wtEditText.setText(getString(R.string.not_connected));
        }
    }
}