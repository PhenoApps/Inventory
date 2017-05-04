package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.content.Context
 * android.content.DialogInterface
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.content.res.Configuration
 * android.graphics.Color
 * android.hardware.usb.UsbConstants
 * android.hardware.usb.UsbDevice
 * android.hardware.usb.UsbDeviceConnection
 * android.hardware.usb.UsbEndpoint
 * android.hardware.usb.UsbInterface
 * android.hardware.usb.UsbManager
 * android.media.MediaScannerConnection
 * android.net.Uri
 * android.os.AsyncTask
 * android.os.Bundle
 * android.R
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.util.Log
 * android.view.Gravity
 * android.view.KeyEvent
 * android.view.LayoutInflater
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View.OnLongClickListener
 * android.view.View
 * android.view.ViewGroup
 * android.view.ViewGroup.LayoutParams
 * android.view.WindowManager
 * android.view.inputmethod.InputMethodManager
 * android.widget.AdapterView
 * android.widget.ArrayAdapter
 * android.widget.Button
 * android.widget.EditText
 * android.widget.ImageView
 * android.widget.LinearLayout
 * android.widget.ListView
 * android.widget.ScrollView
 * android.widget.TableLayout
 * android.widget.TableLayout.LayoutParams
 * android.widget.TableRow
 * android.widget.TextView
 * android.widget.Toast
 *
 * org.wheatgenetics.inventory.InventoryDir
 * org.wheatgenetics.inventory.InventoryRecord
 * org.wheatgenetics.inventory.InventoryRecords
 * org.wheatgenetics.inventory.R
 * org.wheatgenetics.inventory.SamplesTable
 * org.wheatgenetics.inventory.SharedPreferences
 * org.wheatgenetics.inventory.Utils
 */

public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    private static final java.lang.String TAG = "Inventory";

    private static int position = 1;


    // region Private Fields
    // region Widget Private Fields
    private android.support.v4.widget.DrawerLayout       drawerLayout         ;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;

    private android.widget.TextView boxTextView              ;
    private android.widget.EditText envidEditText, wtEditText;

    private android.widget.TableLayout tableLayout;
    private android.widget.ScrollView  scrollView ;
    // endregion


    private org.wheatgenetics.inventory.SamplesTable      samplesTable     ;
    private android.hardware.usb.UsbDevice                usbDevice        ;
    private org.wheatgenetics.inventory.SharedPreferences sharedPreferences;
    private java.lang.String                              box              ;

    private org.wheatgenetics.inventory.ChangeLogAlertDialog changeLogAlertDialog = null;
    // endregion


    // region Class Methods
    // region Log Class Methods
    private static int sendVerboseLogMsg(final java.lang.String msg)
    { return android.util.Log.v(org.wheatgenetics.inventory.MainActivity.TAG, msg); }

    private static int sendInfoLogMsg(final java.lang.String msg)
    { return android.util.Log.i(org.wheatgenetics.inventory.MainActivity.TAG, msg); }

    private static int sendWarnLogMsg(final java.lang.String msg)
    { return android.util.Log.w(org.wheatgenetics.inventory.MainActivity.TAG, msg); }

    private static int sendErrorLogMsg(final java.lang.String msg)
    { return android.util.Log.e(org.wheatgenetics.inventory.MainActivity.TAG, msg); }

    private static int sendErrorLogMsg(final java.lang.Exception exception)
    {
        assert exception != null;
        return org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(exception.getMessage());
    }
    // endregion


    // region Toast Class Methods
    private static void showToast(final android.content.Context context,
    final java.lang.CharSequence text, final int duration)
    { android.widget.Toast.makeText(context, text, duration).show(); }

    static private void showToast(
    final android.content.Context context, final java.lang.CharSequence text)
    {
        org.wheatgenetics.inventory.MainActivity.showToast(
            context, text, android.widget.Toast.LENGTH_SHORT);
    }
    // endregion
    // endregion


    // region Overridden Methods
    @Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.inventory.R.layout.main_layout);
        org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg("onCreate()");

        this.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.inventory.R.id.toolbar);

            this.setSupportActionBar(toolbar);
            assert toolbar != null;
            toolbar.bringToFront();
        }

        {
            final android.support.v7.app.ActionBar supportActionBar = this.getSupportActionBar();

            if (supportActionBar != null)
            {
                supportActionBar.setTitle(null);
                supportActionBar.getThemedContext();         // This appears to do nothing.  Remove?
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled     (true);
            }
        }

        this.drawerLayout = (android.support.v4.widget.DrawerLayout)
            this.findViewById(org.wheatgenetics.inventory.R.id.drawer_layout);
        {
            final android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView)
                this.findViewById(org.wheatgenetics.inventory.R.id.nvView);
            assert navigationView != null;
            navigationView.setNavigationItemSelectedListener(
                new android.support.design.widget.NavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(final android.view.MenuItem item)
                    {
                        return org.wheatgenetics.inventory.MainActivity.this.selectNavigationItem(
                            item);
                    }
                });
        }
        this.actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,
            this.drawerLayout, org.wheatgenetics.inventory.R.string.drawer_open,
            org.wheatgenetics.inventory.R.string.drawer_close)
            {
                @Override
                public void onDrawerOpened(final android.view.View drawerView)
                {
                    final android.widget.TextView personTextView = (android.widget.TextView)
                        findViewById(org.wheatgenetics.inventory.R.id.nameLabel);
                    assert org.wheatgenetics.inventory.MainActivity.this.sharedPreferences != null;
                    assert personTextView                                                  != null;
                    personTextView.setText(
                        org.wheatgenetics.inventory.MainActivity.this.sharedPreferences.getName());
                }

                @Override
                public void onDrawerClosed(final android.view.View drawerView) {}
            };
        this.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        assert this.drawerLayout != null;
        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);

        this.boxTextView =
            (android.widget.TextView) this.findViewById(org.wheatgenetics.inventory.R.id.tvBoxNum);
        assert this.boxTextView != null;
        this.boxTextView.setText("");

        this.envidEditText =
            (android.widget.EditText) this.findViewById(org.wheatgenetics.inventory.R.id.etInput);

        this.wtEditText =
            (android.widget.EditText) this.findViewById(org.wheatgenetics.inventory.R.id.etWeight);
        assert this.wtEditText != null;
        this.wtEditText.setText(this.getString(org.wheatgenetics.inventory.R.string.not_connected));

        this.tableLayout = (android.widget.TableLayout)
            this.findViewById(org.wheatgenetics.inventory.R.id.tlInventory);
        this.scrollView =
            (android.widget.ScrollView ) this.findViewById(org.wheatgenetics.inventory.R.id.svData);

        this.samplesTable = new org.wheatgenetics.inventory.SamplesTable(this);

        {
            final android.widget.Button setBoxButton =
                (android.widget.Button) this.findViewById(org.wheatgenetics.inventory.R.id.btBox);
            assert setBoxButton != null;
            setBoxButton.setOnClickListener(new android.view.View.OnClickListener()
                {
                    @Override
                    public void onClick(final android.view.View v)
                    { org.wheatgenetics.inventory.MainActivity.this.setBox(); }
                });
        }

        assert this.tableLayout != null;
        this.tableLayout.setOnClickListener(new android.view.View.OnClickListener()
            {
                @Override
                public void onClick(final android.view.View v) {}
            });

        this.usbDevice =
            this.getIntent().getParcelableExtra(android.hardware.usb.UsbManager.EXTRA_DEVICE);

        assert this.envidEditText != null;
        this.envidEditText.setOnKeyListener(new android.view.View.OnKeyListener()
            {
                @Override
                public boolean onKey(final android.view.View v, final int keyCode,
                final android.view.KeyEvent event)
                {
                    assert event != null;
                    if (keyCode == android.view.KeyEvent.KEYCODE_ENTER)
                    {
                        {
                            final android.view.inputmethod.InputMethodManager inputMethodManager =
                                (android.view.inputmethod.InputMethodManager)
                                org.wheatgenetics.inventory.MainActivity.this.getSystemService(
                                    android.content.Context.INPUT_METHOD_SERVICE);
                            assert inputMethodManager != null;
                            inputMethodManager.showSoftInput(
                                org.wheatgenetics.inventory.MainActivity.this.envidEditText   ,
                                android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                        if (event.getAction() != android.view.KeyEvent.ACTION_DOWN) return true;
                        org.wheatgenetics.inventory.MainActivity.this.addRecord();
                        org.wheatgenetics.inventory.MainActivity.this.envidEditText.requestFocus(); // Set focus back to Enter box
                    }

                    if (keyCode == android.view.KeyEvent.KEYCODE_NUMPAD_ENTER)
                    {
                        if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) return true;
                        if (event.getAction() == android.view.KeyEvent.ACTION_UP)
                            org.wheatgenetics.inventory.MainActivity.this.addRecord();
                        org.wheatgenetics.inventory.MainActivity.this.envidEditText.requestFocus(); // Set focus back to Enter box
                    }
                    return false;
                }
            });

        this.wtEditText.setOnKeyListener(new android.view.View.OnKeyListener()
            {
                @Override
                public boolean onKey(final android.view.View v, final int keyCode,
                final android.view.KeyEvent event)
                {
                    assert event != null;
                    if (keyCode == android.view.KeyEvent.KEYCODE_ENTER
                    ||  keyCode == android.view.KeyEvent.KEYCODE_NUMPAD_ENTER)
                    {
                        {
                            final android.view.inputmethod.InputMethodManager inputMethodManager =
                                (android.view.inputmethod.InputMethodManager)
                                org.wheatgenetics.inventory.MainActivity.this.getSystemService(
                                    android.content.Context.INPUT_METHOD_SERVICE);
                            assert inputMethodManager != null;
                            inputMethodManager.showSoftInput(
                                org.wheatgenetics.inventory.MainActivity.this.envidEditText   ,
                                android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                        if (event.getAction() != android.view.KeyEvent.ACTION_DOWN) return true;
                        org.wheatgenetics.inventory.MainActivity.this.addRecord();

                        if (org.wheatgenetics.inventory.MainActivity.this.usbDevice != null)
                            org.wheatgenetics.inventory.MainActivity.this.wtEditText.setText("");
                        org.wheatgenetics.inventory.MainActivity.this.envidEditText.requestFocus(); // Set focus back to Enter box
                    }

                    if (keyCode == android.view.KeyEvent.KEYCODE_NUMPAD_ENTER)
                    {
                        if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) return true;
                        if (event.getAction() == android.view.KeyEvent.ACTION_UP)
                            org.wheatgenetics.inventory.MainActivity.this.addRecord();
                        org.wheatgenetics.inventory.MainActivity.this.envidEditText.requestFocus(); // Set focus back to Enter box
                    }
                    return false;
                }
            });

        try
        { this.makeFileDiscoverable(org.wheatgenetics.inventory.InventoryDir.createIfMissing()); }
        catch (final java.io.IOException e)
        { org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(e); }
        this.addTableRows();
        this.goToBottom();

        assert this.sharedPreferences != null;
        this.sharedPreferences = new
            org.wheatgenetics.inventory.SharedPreferences(this.getSharedPreferences("Settings", 0));

        if (!this.sharedPreferences.firstNameIsSet()) this.setPerson   ();
        if (!this.sharedPreferences.getIgnoreScale()) this.connectScale();

        int v = 0;
        try { v = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode; }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        { org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(e); }
        if (!this.sharedPreferences.updateVersionIsSet(v))
        {
            this.sharedPreferences.setUpdateVersion(v);
            this.showChangeLog();
        }
    }

    @Override
    protected void onPostCreate(final android.os.Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        this.actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg("onStart()");
    }

    @Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu) { return true; }

    @Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        assert this.actionBarDrawerToggle != null;
        if (this.actionBarDrawerToggle.onOptionsItemSelected(item)) return true;

        assert item != null;
        switch (item.getItemId())
        {
            case android.R.id.home:
                assert this.drawerLayout != null;
                this.drawerLayout.openDrawer(android.support.v4.view.GravityCompat.START);
                return true;
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(final android.content.res.Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        this.actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    // endregion


    private void showToast(final java.lang.CharSequence text)
    { org.wheatgenetics.inventory.MainActivity.showToast(this, text); }

    private void makeFileDiscoverable(final java.io.File file)
    {
        if (file != null)
        {
            android.media.MediaScannerConnection.scanFile(this,
                org.wheatgenetics.inventory.Utils.makeStringArray(file.getPath()), null, null);
            this.sendBroadcast(new android.content.Intent(
                android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                android.net.Uri.fromFile(file)                       ));
        }
    }


    // region addTableRow()
    private android.widget.TextView makeTextView(
    final java.lang.CharSequence text, final float initWeight)
    {
        final android.widget.TextView textView = new android.widget.TextView(this);

        textView.setGravity  (android.view.Gravity.CENTER | android.view.Gravity.BOTTOM);
        textView.setTextColor(android.graphics.Color.BLACK                             );
        textView.setTextSize (20.0f                                                    );
        textView.setText     (text                                                     );
        textView.setLayoutParams(new android.widget.TableRow.LayoutParams(
            /* w          => */ 0                                               ,
            /* h          => */ android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            /* initWeight => */ initWeight                                      ));

        return textView;
    }

    private boolean delete(final java.lang.Object tag)
    {
        assert tag != null;
        final java.lang.String tagArray[] = ((java.lang.String) tag).split(",");
        final java.lang.String box        =                            tagArray[0] ;
        final java.lang.String env        =                            tagArray[1] ;
        final int              num        = java.lang.Integer.parseInt(tagArray[2]);

        final android.app.AlertDialog.Builder builder =
            new android.app.AlertDialog.Builder(org.wheatgenetics.inventory.MainActivity.this);
        builder.setTitle(this.getString(org.wheatgenetics.inventory.R.string.delete_entry));
        builder.setMessage(this.getString(org.wheatgenetics.inventory.R.string.delete) + env + "?")
            .setCancelable(true)
            .setPositiveButton(this.getString(org.wheatgenetics.inventory.R.string.yes),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.inventory.MainActivity.this.samplesTable.delete(
                            new org.wheatgenetics.inventory.InventoryRecord(box, env, num));
                        org.wheatgenetics.inventory.MainActivity.this.addTableRows();
                    }
                })
            .setNegativeButton(this.getString(org.wheatgenetics.inventory.R.string.no),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert dialog != null;
                        dialog.cancel();
                    }
                });
        builder.create().show();

        return false;
    }

    /**
     * Adds a new entry to the end of the TableView
     */
    private void addTableRow(final org.wheatgenetics.inventory.InventoryRecord inventoryRecord)
    {
        assert this.envidEditText != null;
        this.envidEditText.setText("");

        final android.widget.TableRow tableRow = new android.widget.TableRow(this);
        tableRow.setLayoutParams(new android.widget.TableLayout.LayoutParams(
            android.widget.TableLayout.LayoutParams.WRAP_CONTENT,
            android.widget.TableLayout.LayoutParams.WRAP_CONTENT));

        assert inventoryRecord != null;
        tableRow.addView(this.makeTextView(inventoryRecord.getPositionAsString(), 0.16f));
        tableRow.addView(this.makeTextView(inventoryRecord.getBox()             , 0.16f));

        {
            final android.widget.TextView envidTextView =
                this.makeTextView(inventoryRecord.getEnvId(), 0.5f);
            assert envidTextView != null;
            envidTextView.setTag          (inventoryRecord.getTag());
            envidTextView.setLongClickable(true                    );

            envidTextView.setOnLongClickListener(new android.view.View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(final android.view.View v)
                    {
                        assert v != null;
                        return org.wheatgenetics.inventory.MainActivity.this.delete(v.getTag());
                    }
                });

            tableRow.addView(envidTextView);
        }

        tableRow.addView(this.makeTextView(inventoryRecord.getWt(), 0.16f));

        assert this.tableLayout != null;
        this.tableLayout.addView(tableRow, new android.view.ViewGroup.LayoutParams(  // Add tableRow to tableLayout.
            android.widget.TableLayout.LayoutParams.MATCH_PARENT,
            android.widget.TableLayout.LayoutParams.MATCH_PARENT));
    }
    // endregion


    private void addTableRows()
    {
        assert this.tableLayout != null;
        this.tableLayout.removeAllViews();

        assert this.samplesTable != null;
        for (final org.wheatgenetics.inventory.InventoryRecord inventoryRecord:
        this.samplesTable.getAll())
        {
            assert inventoryRecord != null;
            inventoryRecord.sendErrorLogMsg(org.wheatgenetics.inventory.MainActivity.TAG);
            this.addTableRow(inventoryRecord);
            org.wheatgenetics.inventory.MainActivity.position = inventoryRecord.getPosition() + 1;
        }
        this.samplesTable.close();
    }

    private void goToBottom()
    {
        assert this.scrollView != null;
        this.scrollView.post(new java.lang.Runnable()
            {
                @Override
                public void run()
                {
                    assert org.wheatgenetics.inventory.MainActivity.this.scrollView != null;
                    org.wheatgenetics.inventory.MainActivity.this.scrollView.fullScroll(
                        android.widget.ScrollView.FOCUS_DOWN);

                    assert org.wheatgenetics.inventory.MainActivity.this.envidEditText != null;
                    org.wheatgenetics.inventory.MainActivity.this.envidEditText.requestFocus();
                }
            });
    }

    /**
     * Adds values in widgets to samples database table and to bottom half of screen.
     */
    private void addRecord()
    {
        {
            assert this.envidEditText != null;
            final java.lang.String envid = this.envidEditText.getText().toString();
            assert envid != null;
            if (envid.equals("")) return;                              // check for empty user input
        }

        assert this.boxTextView       != null;
        assert this.sharedPreferences != null;
        assert this.wtEditText        != null;
        final org.wheatgenetics.inventory.InventoryRecord inventoryRecord =
            new org.wheatgenetics.inventory.InventoryRecord(
                /* box      => */ this.boxTextView.getText().toString()              ,
                /* envid    => */ this.envidEditText.getText().toString()            ,
                /* person   => */ this.sharedPreferences.getSafeName()               ,
                /* position => */ org.wheatgenetics.inventory.MainActivity.position++,
                /* wt       => */ this.wtEditText.getText().toString()               );

        assert this.samplesTable != null;
        this.samplesTable.add(inventoryRecord);
        this.addTableRow     (inventoryRecord);

        this.goToBottom();
    }

    private class CustomListAdapter extends android.widget.ArrayAdapter<java.lang.String>
    {
        private final android.content.Context context      ;
        private final java.lang.String        color_names[];
        private final java.lang.Integer       image_ids  [];

        CustomListAdapter(final android.app.Activity context,
        final java.lang.Integer image_ids[], final java.lang.String color_names[])
        {
            super(context, org.wheatgenetics.inventory.R.layout.appline, color_names);

            this.context     = context    ;
            this.color_names = color_names;
            this.image_ids   = image_ids  ;
        }

        @Override
        public android.view.View getView(final int position, final android.view.View convertView,
        final android.view.ViewGroup parent)
        {
            android.view.View singleRow;
            {
                final android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater)
                    this.context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                singleRow = layoutInflater.inflate(
                    org.wheatgenetics.inventory.R.layout.appline, null, true);
            }
            assert singleRow != null;
            {
                final android.widget.TextView textView = (android.widget.TextView)
                    singleRow.findViewById(org.wheatgenetics.inventory.R.id.txt);
                assert textView != null;
                textView.setText(this.color_names[position]);
            }
            {
                final android.widget.ImageView imageView = (android.widget.ImageView)
                    singleRow.findViewById(org.wheatgenetics.inventory.R.id.img);
                assert imageView != null;
                imageView.setImageResource(this.image_ids[position]);
            }
            return singleRow;
        }
    }

    private void deleteAll()
    {
        this.samplesTable.deleteAll();
        this.tableLayout.removeAllViews();
        org.wheatgenetics.inventory.MainActivity.position = 1;
    }


    // region Drawer Methods
    // region Drawer Subsubaction Methods
    private void shareFile(final java.io.File file, final java.lang.String fileName)
    {
        this.makeFileDiscoverable(file);
        this.showToast(this.getString(org.wheatgenetics.inventory.R.string.export_success));

        final android.content.Intent intent =
            new android.content.Intent(android.content.Intent.ACTION_SEND);

        intent.setType ("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_STREAM,
            org.wheatgenetics.inventory.InventoryDir.parse(fileName));

        this.startActivity(android.content.Intent.createChooser(intent,
            this.getString(org.wheatgenetics.inventory.R.string.sending_file)));
    }
    // endregion


    // region Drawer Subaction Methods
    private void exportCSV()
    {
        {
            assert this.samplesTable != null;
            final org.wheatgenetics.inventory.InventoryRecords inventoryRecords =
                this.samplesTable.getAll();
            this.samplesTable.close();
            {
                final java.lang.String fileName =
                    org.wheatgenetics.inventory.Utils.getExportFileName() + ".csv";
                assert inventoryRecords != null;
                try { this.shareFile(inventoryRecords.writeCSV(fileName), fileName); }
                catch (final java.io.IOException e)
                {
                    org.wheatgenetics.inventory.MainActivity.showToast(
                        this.getBaseContext(), e.getMessage());
                }
            }
        }
        this.deleteAll();
    }

    private void exportSQL()
    {
        {
            assert this.samplesTable != null;
            final org.wheatgenetics.inventory.InventoryRecords inventoryRecords =
                this.samplesTable.getAll();
            final java.lang.String boxList = this.samplesTable.getBoxList();
            this.samplesTable.close();
            {
                final java.lang.String fileName =
                    org.wheatgenetics.inventory.Utils.getExportFileName() + ".sql";
                assert inventoryRecords != null;
                try { this.shareFile(inventoryRecords.writeSQL(fileName, boxList), fileName); }
                catch (final java.io.IOException e)
                {
                    org.wheatgenetics.inventory.MainActivity.showToast(
                        this.getBaseContext(), e.getMessage());
                }
            }
        }
        this.deleteAll();
    }

    private void showChangeLog()
    {
        if (this.changeLogAlertDialog == null)
        {
            final android.content.res.Resources resources = this.getResources();

            assert resources != null;
            final java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(
                resources.openRawResource(org.wheatgenetics.inventory.R.raw.changelog_releases));

            this.changeLogAlertDialog = new org.wheatgenetics.inventory.ChangeLogAlertDialog(
                /* context            => */ this                                         ,
                /* applicationContext => */ this.getApplicationContext()                 ,
                /* inputStreamReader  => */ inputStreamReader                            ,
                /* activityClass      => */ org.wheatgenetics.inventory.MainActivity.this,
                /* title              => */
                    resources.getString(org.wheatgenetics.inventory.R.string.updatemsg),
                /* positiveButtonText => */
                    resources.getString(org.wheatgenetics.inventory.R.string.ok));
        }
        try { this.changeLogAlertDialog.show(); }
        catch (final java.io.IOException e) { throw new java.lang.RuntimeException(e); }
    }

    private void showOtherAppsDialog()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            final android.widget.ListView listView = new android.widget.ListView(this);

            listView.setDivider(null);
            listView.setDividerHeight(0);
            {
                final java.lang.String links[] = {                      // TODO: Update these links.
                    "https://play.google.com/store/apps/details?id=com.fieldbook.tracker",
                    "http://wheatgenetics.org/apps"                                      ,
                    "http://wheatgenetics.org/apps"                                      };
                listView.setOnItemClickListener(
                    new android.widget.AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(final android.widget.AdapterView<?> parent,
                        final android.view.View view, final int position, final long id)
                        {
                            switch (position)
                            {
                                case 0:
                                case 1:
                                case 2:
                                    org.wheatgenetics.inventory.MainActivity.this.startActivity(
                                        new android.content.Intent(
                                            android.content.Intent.ACTION_VIEW,
                                            android.net.Uri.parse(links[position])));
                                break;
                            }
                        }
                    });
            }
            {
                final java.lang.Integer appIconIDs[] = {
                    org.wheatgenetics.inventory.R.drawable.other_ic_field_book,
                    org.wheatgenetics.inventory.R.drawable.other_ic_coordinate,
                    org.wheatgenetics.inventory.R.drawable.other_ic_1kk       };

                final java.lang.String appNames[] = new java.lang.String[3];
                appNames[0] = "Field Book";
                appNames[1] = "Coordinate";
                appNames[2] = "1KK"       ;
                //appNames[3] = "Intercross";
                //appNames[4] = "Rangle"    ;

                listView.setAdapter(new org.wheatgenetics.inventory.MainActivity.CustomListAdapter(
                    this, appIconIDs, appNames));
            }

            builder.setCancelable(true);
            builder.setTitle(
                this.getResources().getString(org.wheatgenetics.inventory.R.string.otherapps));
            builder.setView(listView);
        }
        builder.setNegativeButton(
            this.getResources().getString(org.wheatgenetics.inventory.R.string.ok),
            new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert dialog != null;
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    // endregion


    // region Drawer Action Methods
    private void setPerson()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle     (this.getResources().getString(
            org.wheatgenetics.inventory.R.string.set_person));
        {
            final android.view.View personView = this.getLayoutInflater().inflate(
                org.wheatgenetics.inventory.R.layout.person, new android.widget.LinearLayout(this),
                false);

            assert personView != null;
            final android.widget.EditText firstNameEditText = (android.widget.EditText)
                personView.findViewById(org.wheatgenetics.inventory.R.id.firstName);
            final android.widget.EditText lastNameEditText = (android.widget.EditText)
                personView.findViewById(org.wheatgenetics.inventory.R.id.lastName);

            assert this.sharedPreferences != null;
            assert firstNameEditText      != null;
            assert lastNameEditText       != null;
            firstNameEditText.setText(this.sharedPreferences.getFirstName());
            lastNameEditText.setText (this.sharedPreferences.getLastName ());

            builder.setView(personView);
            builder.setPositiveButton(
                this.getResources().getString(org.wheatgenetics.inventory.R.string.ok),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        final java.lang.String firstName =
                            firstNameEditText.getText().toString().trim();
                        final java.lang.String lastName =
                            lastNameEditText.getText().toString().trim();

                        if (firstName.length() == 0 | lastName.length() == 0)
                        {
                            org.wheatgenetics.inventory.MainActivity.this.showToast(
                                getResources().getString(
                                    org.wheatgenetics.inventory.R.string.no_blank));
                            org.wheatgenetics.inventory.MainActivity.this.setPerson();
                            return;
                        }

                        org.wheatgenetics.inventory.MainActivity.this.showToast(
                            getResources().getString(
                                org.wheatgenetics.inventory.R.string.person_set) +
                            " " + firstName + " " + lastName);
                        assert
                            org.wheatgenetics.inventory.MainActivity.this.sharedPreferences != null;
                        org.wheatgenetics.inventory.MainActivity.this.sharedPreferences.setName(
                            firstName, lastName);
                    }
                });
        }
        builder.show();
    }

    private void connectScale()
    {
        if (this.usbDevice == null)
        {
            final android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager)
                this.getSystemService(android.content.Context.USB_SERVICE);

            assert usbManager != null;
            final java.util.HashMap<java.lang.String, android.hardware.usb.UsbDevice> deviceList =
                usbManager.getDeviceList();

            assert deviceList != null;
            for (final android.hardware.usb.UsbDevice usbDevice : deviceList.values())
            {
                assert usbDevice != null;
                this.usbDevice = usbDevice;
                org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(java.lang.String.format(
                    "name=%s deviceId=%d productId=%d vendorId=%d " +
                        "deviceClass=%d subClass=%d protocol=%d interfaceCount=%d",
                    this.usbDevice.getDeviceName()    , this.usbDevice.getDeviceId()      ,
                    this.usbDevice.getProductId()     , this.usbDevice.getVendorId()      ,
                    this.usbDevice.getDeviceClass()   , this.usbDevice.getDeviceSubclass(),
                    this.usbDevice.getDeviceProtocol(), this.usbDevice.getInterfaceCount()));
                break;
            }
        }

        if (this.usbDevice != null)
        {
            assert this.wtEditText != null;
            this.wtEditText.setText("0");
            new org.wheatgenetics.inventory.MainActivity.ScaleListener().execute();
        }
        else
            new android.app.AlertDialog.Builder(org.wheatgenetics.inventory.MainActivity.this)
                .setTitle     (this.getString(org.wheatgenetics.inventory.R.string.no_scale     ))
                .setMessage   (this.getString(org.wheatgenetics.inventory.R.string.connect_scale))
                .setCancelable(false                                                             )
                .setPositiveButton(this.getString(org.wheatgenetics.inventory.R.string.try_again),
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        { org.wheatgenetics.inventory.MainActivity.this.connectScale(); }
                    })
                .setNegativeButton(this.getString(org.wheatgenetics.inventory.R.string.ignore),
                    new android.content.DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(final android.content.DialogInterface dialog,
                        final int which)
                        {
                            org.wheatgenetics.inventory.MainActivity.this.
                                sharedPreferences.setIgnoreScaleToTrue();
                            assert dialog != null;
                            dialog.cancel();
                        }
                    })
                .show();
    }

    private void export()
    {
        new android.app.AlertDialog.Builder(org.wheatgenetics.inventory.MainActivity.this)
            .setTitle         (this.getString(org.wheatgenetics.inventory.R.string.export_data  ))
            .setMessage       (this.getString(org.wheatgenetics.inventory.R.string.export_choice))
            .setPositiveButton(this.getString(org.wheatgenetics.inventory.R.string.export_csv   ),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    { org.wheatgenetics.inventory.MainActivity.this.exportCSV(); }
                })
            .setNegativeButton(this.getString(org.wheatgenetics.inventory.R.string.export_sql),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    { org.wheatgenetics.inventory.MainActivity.this.exportSQL(); }
                })
            .setNeutralButton(this.getString(org.wheatgenetics.inventory.R.string.cancel),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert dialog != null;
                        dialog.cancel();
                    }
                })
            .show();
    }

    private void clearAll()
    {
        final android.app.AlertDialog.Builder builder =
            new android.app.AlertDialog.Builder(org.wheatgenetics.inventory.MainActivity.this);

        builder.setMessage(this.getString(org.wheatgenetics.inventory.R.string.delete_msg_1))
            .setCancelable    (false                                                          )
            .setTitle         (this.getString(org.wheatgenetics.inventory.R.string.clear_data))
            .setPositiveButton(this.getString(org.wheatgenetics.inventory.R.string.yes),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert org.wheatgenetics.inventory.MainActivity.this.boxTextView != null;
                        org.wheatgenetics.inventory.MainActivity.this.boxTextView.setText("");
                        org.wheatgenetics.inventory.MainActivity.this.showToast(
                            org.wheatgenetics.inventory.MainActivity.this.getString(
                                org.wheatgenetics.inventory.R.string.data_deleted));
                        org.wheatgenetics.inventory.MainActivity.this.deleteAll();
                    }
                })
            .setNegativeButton(this.getString(org.wheatgenetics.inventory.R.string.no),
                new android.content.DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        assert dialog != null;
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void showAboutDialog()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        {
            final android.view.View personView = this.getLayoutInflater().inflate(
                org.wheatgenetics.inventory.R.layout.about, new android.widget.LinearLayout(this),
                false);

            {
                assert personView != null;
                final android.widget.TextView versionTextView = (android.widget.TextView)
                    personView.findViewById(org.wheatgenetics.inventory.R.id.tvVersion);
                try
                {
                    final android.content.pm.PackageInfo packageInfo =
                        this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
                    assert packageInfo     != null;
                    assert versionTextView != null;
                    versionTextView.setText(this.getResources().getString(
                            org.wheatgenetics.inventory.R.string.versiontitle) +
                        " " + packageInfo.versionName);
                }
                catch (final android.content.pm.PackageManager.NameNotFoundException e)
                { org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(e); }
                versionTextView.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.inventory.MainActivity.this.showChangeLog(); }
                    });
            }

            {
                final android.widget.TextView otherAppsTextView =(android.widget.TextView)
                    personView.findViewById(org.wheatgenetics.inventory.R.id.tvOtherApps);
                assert otherAppsTextView != null;
                otherAppsTextView.setOnClickListener(new android.view.View.OnClickListener()
                    {
                        @Override
                        public void onClick(final android.view.View v)
                        { org.wheatgenetics.inventory.MainActivity.this.showOtherAppsDialog(); }
                    });
            }

            builder.setCancelable(true);
            builder.setTitle     (this.getResources().getString(
                org.wheatgenetics.inventory.R.string.about));
            builder.setView(personView);
        }
        builder.setNegativeButton(
            this.getResources().getString(org.wheatgenetics.inventory.R.string.ok),
            new android.content.DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.dismiss();
                }
            });
        builder.show();
    }
    // endregion


    // region Drawer Selector Method
    private boolean selectNavigationItem(final android.view.MenuItem menuItem)
    {
        assert menuItem != null;
        switch (menuItem.getItemId())
        {
            case org.wheatgenetics.inventory.R.id.person:
                this.setPerson();
                break;
            case org.wheatgenetics.inventory.R.id.scaleConnect:
                this.connectScale();
                break;
            case org.wheatgenetics.inventory.R.id.export:
                this.export();
                break;
            case org.wheatgenetics.inventory.R.id.clearData:
                this.clearAll();
                break;
            case org.wheatgenetics.inventory.R.id.about:
                this.showAboutDialog();
                break;
        }

        assert this.drawerLayout != null;
        this.drawerLayout.closeDrawers();

        return true;
    }
    // endregion
    // endregion


    private void setBox()
    {
        final android.widget.EditText boxEditText = new android.widget.EditText(this);
        boxEditText.setText      (this.box);
        boxEditText.selectAll    ();
        boxEditText.setSingleLine();

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(this.getString(org.wheatgenetics.inventory.R.string.setbox));
        builder.setView (boxEditText);

        builder.setPositiveButton(this.getString(org.wheatgenetics.inventory.R.string.ok),
            new android.content.DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    {
                        final java.lang.String value = boxEditText.getText().toString().trim();

                        assert org.wheatgenetics.inventory.MainActivity.this.boxTextView != null;
                        org.wheatgenetics.inventory.MainActivity.this.boxTextView.setText(value);
                        org.wheatgenetics.inventory.MainActivity.this.box = value;
                    }

                    final android.view.inputmethod.InputMethodManager inputMethodManager =
                        (android.view.inputmethod.InputMethodManager)
                        org.wheatgenetics.inventory.MainActivity.this.getSystemService(
                            android.content.Context.INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(boxEditText.getWindowToken(), 0);
                }
            });

        builder.setNegativeButton(this.getString(org.wheatgenetics.inventory.R.string.cancel),
            new android.content.DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    assert dialog != null;
                    dialog.cancel();

                    final android.view.inputmethod.InputMethodManager inputMethodManager =
                        (android.view.inputmethod.InputMethodManager)
                        org.wheatgenetics.inventory.MainActivity.this.getSystemService(
                            android.content.Context.INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(boxEditText.getWindowToken(), 0);
                }
            });

        builder.create().show();
    }

    private class ScaleListener extends android.os.AsyncTask<
    java.lang.Void, java.lang.Double, java.lang.Void>
    {
        private double lastWeight = 0;

        @Override
        protected java.lang.Void doInBackground(final java.lang.Void... params)
        {
            org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg("start transfer");

            if (org.wheatgenetics.inventory.MainActivity.this.usbDevice == null)
            {
                org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg("no device");
                return null;
            }
            final android.hardware.usb.UsbInterface usbInterface =
                org.wheatgenetics.inventory.MainActivity.this.usbDevice.getInterface(0);

            assert usbInterface != null;
            org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(
                java.lang.String.format("endpoint count = %d", usbInterface.getEndpointCount()));
            final android.hardware.usb.UsbEndpoint usbEndpoint = usbInterface.getEndpoint(0);

            assert usbEndpoint != null;
            org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(
                java.lang.String.format("usbEndpoint direction = %d out = %d in = %d",
                usbEndpoint.getDirection(), android.hardware.usb.UsbConstants.USB_DIR_OUT,
                android.hardware.usb.UsbConstants.USB_DIR_IN));

            final android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager)
                org.wheatgenetics.inventory.MainActivity.this.getSystemService(
                    android.content.Context.USB_SERVICE);

            assert usbManager != null;
            final android.hardware.usb.UsbDeviceConnection usbDeviceConnection =
                usbManager.openDevice(org.wheatgenetics.inventory.MainActivity.this.usbDevice);

            assert usbDeviceConnection != null;
            usbDeviceConnection.claimInterface(usbInterface, true);

            final byte data[] = new byte[128];

            while (true)
            {
                final int length = usbDeviceConnection.bulkTransfer(
                    usbEndpoint, data, data.length, /* timeout => */ 2000);

                if (length != 6)
                {
                    org.wheatgenetics.inventory.MainActivity.sendErrorLogMsg(
                        java.lang.String.format("invalid length: %d", length));
                    return null;
                }

                final byte report = data[0];
                final byte status = data[1];
                // final byte exp = data[3];
                final short weightLSB = (short) (data[4] & 0xff);
                final short weightMSB = (short) (data[5] & 0xff);

                // org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(java.lang.String.format(
                //   "report=%x status=%x exp=%x lsb=%x msb=%x",
                //   report, status, exp, weightLSB, weightMSB));

                if (report != 3)
                {
                    org.wheatgenetics.inventory.MainActivity.sendVerboseLogMsg(
                        java.lang.String.format("scale status error %d", status));
                    return null;
                }

                double weightGrams = weightLSB + weightMSB * 256.0;
                if (org.wheatgenetics.inventory.MainActivity.this.usbDevice.getProductId() == 519)
                    weightGrams /= 10.0;
                final double zeroGrams = 0;
                final double weight    = weightGrams - zeroGrams;

                switch (status)
                {
                    case 1:
                        org.wheatgenetics.inventory.MainActivity.sendWarnLogMsg(
                            "Scale reports FAULT!\n");
                        break;
                    case 3:
                        org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg("Weighing...");
                        if (this.lastWeight != weight) this.publishProgress(weight);
                        break;
                    case 2:
                    case 4:
                        if (this.lastWeight != weight)
                        {
                            org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg(
                                java.lang.String.format("Final Weight: %f", weight));
                            this.publishProgress(weight);
                        }
                        break;
                    case 5:
                        org.wheatgenetics.inventory.MainActivity.sendWarnLogMsg(
                            "Scale reports Under Zero");
                        if (this.lastWeight != weight) this.publishProgress(0.0);
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

                this.lastWeight = weight;
            }
        }

        @Override
        protected void onProgressUpdate(final java.lang.Double... values)
        {
            org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg("update progress");

            assert values != null;
            final java.lang.String weightText = java.lang.String.format("%.1f", values[0]);
            org.wheatgenetics.inventory.MainActivity.sendInfoLogMsg(weightText);

            assert org.wheatgenetics.inventory.MainActivity.this.wtEditText != null;
            org.wheatgenetics.inventory.MainActivity.this.wtEditText.setText(weightText);
            org.wheatgenetics.inventory.MainActivity.this.wtEditText.invalidate();
        }

        @Override
        protected void onPostExecute(final java.lang.Void result)
        {
            org.wheatgenetics.inventory.MainActivity.showToast(
                org.wheatgenetics.inventory.MainActivity.this.getApplicationContext(),
                org.wheatgenetics.inventory.MainActivity.this.getString(
                    org.wheatgenetics.inventory.R.string.scale_disconnect),
                android.widget.Toast.LENGTH_LONG                          );
            org.wheatgenetics.inventory.MainActivity.this.usbDevice = null;

            assert org.wheatgenetics.inventory.MainActivity.this.wtEditText != null;
            org.wheatgenetics.inventory.MainActivity.this.wtEditText.setText(
                org.wheatgenetics.inventory.MainActivity.this.getString(
                    org.wheatgenetics.inventory.R.string.not_connected));
        }
    }
}