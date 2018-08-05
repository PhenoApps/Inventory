package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager
 * android.content.pm.PackageManager.NameNotFoundException
 * android.Manifest.permission
 * android.os.Bundle
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.support.v4.app.FragmentManager
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuInflater
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
 * org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 *
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 *
 * org.wheatgenetics.usb.Device.Exception
 * org.wheatgenetics.usb.ScaleExceptionAlertDialog
 * org.wheatgenetics.usb.ScaleExceptionAlertDialog.Handler
 * org.wheatgenetics.usb.ScaleReader
 * org.wheatgenetics.usb.ScaleReader.Handler
 *
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.inventory.dataentry.DataEntryFragment
 * org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler
 *
 * org.wheatgenetics.inventory.display.DisplayFragment
 * org.wheatgenetics.inventory.display.DisplayFragment.Handler
 *
 * org.wheatgenetics.inventory.model.InventoryRecord
 * org.wheatgenetics.inventory.model.InventoryRecords
 * org.wheatgenetics.inventory.model.Person
 *
 * org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener
 * org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener.Handler
 *
 * org.wheatgenetics.inventory.InventoryDir
 * org.wheatgenetics.inventory.R
 * org.wheatgenetics.inventory.SamplesTable
 * org.wheatgenetics.inventory.SetPersonAlertDialog
 * org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity implements
org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler      ,
org.wheatgenetics.inventory.display.DisplayFragment.Handler          ,
org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryEnumModifier"})
    private static enum ExportKind { CSV, SQL }

    // region Constants
    private static final int              REQUEST_CODE = 100  ;
    private static final java.lang.String BOX          = "box";
    // endregion

    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout = null;

    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences               ;
    private org.wheatgenetics.usb.ScaleReader                     scaleReaderInstance       = null;
    private org.wheatgenetics.usb.ScaleExceptionAlertDialog       scaleExceptionAlertDialog = null;
    private org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog
        getExportFileNameAlertDialog = null;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog changeLogAlertDialog = null;
    private org.wheatgenetics.zxing.BarcodeScanner           barcodeScanner       = null;

    private org.wheatgenetics.inventory.SetPersonAlertDialog        setPersonAlertDialog = null;
    private org.wheatgenetics.inventory.InventoryDir                inventoryDir         = null;
    private org.wheatgenetics.inventory.SamplesTable                samplesTableInstance = null;
    private org.wheatgenetics.inventory.dataentry.DataEntryFragment dataEntryFragment          ;
    private org.wheatgenetics.inventory.display.DisplayFragment     displayFragment            ;

    private org.wheatgenetics.inventory.MainActivity.ExportKind exportKind         ;
    private java.lang.String                                    box, exportFileName;
    // endregion

    // region Private Methods
    // region Private Private Methods
    private void showToast(final java.lang.CharSequence text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this, text); }

    private void showToast(final int text) { this.showToast(this.getString(text)); }

    private void storePerson(
    @android.support.annotation.NonNull final org.wheatgenetics.inventory.model.Person person)
    {
        assert null != this.sharedPreferences; this.sharedPreferences.setPerson(person);
        this.showToast(this.getString(org.wheatgenetics.inventory.R.string.setPersonMsg) +
            person.toString());
    }

    private void publish(final java.lang.String wt)
    { assert null != this.dataEntryFragment; this.dataEntryFragment.setWt(wt); }

    private void ignoreScale()
    { assert null != this.sharedPreferences; this.sharedPreferences.setIgnoreScaleToTrue(); }

    private void reportException(final org.wheatgenetics.usb.Device.Exception e)
    {
        this.scaleReader().cancel();
        assert null != this.dataEntryFragment; this.dataEntryFragment.clearWt();
        if (null == this.scaleExceptionAlertDialog) this.scaleExceptionAlertDialog =
            new org.wheatgenetics.usb.ScaleExceptionAlertDialog(this,
                new org.wheatgenetics.usb.ScaleExceptionAlertDialog.Handler()
                {
                    @java.lang.Override public void tryAgain()
                    { org.wheatgenetics.inventory.MainActivity.this.connectScale(); }

                    @java.lang.Override public void ignore()
                    { org.wheatgenetics.inventory.MainActivity.this.ignoreScale(); }
                });
        this.scaleExceptionAlertDialog.show(e);
    }

    private org.wheatgenetics.usb.ScaleReader scaleReader()
    {
        if (null == this.scaleReaderInstance)
            this.scaleReaderInstance = new org.wheatgenetics.usb.ScaleReader(this,
                new org.wheatgenetics.usb.ScaleReader.Handler()
                {
                    @java.lang.Override public void publish(final java.lang.String s)
                    { org.wheatgenetics.inventory.MainActivity.this.publish(s); }

                    @java.lang.Override public void reportException(
                    final org.wheatgenetics.usb.Device.Exception e)
                    { org.wheatgenetics.inventory.MainActivity.this.reportException(e); }
                });
        return this.scaleReaderInstance;
    }

    private void createInventoryDirIfMissing()
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        if (null == this.inventoryDir)
            this.inventoryDir = new org.wheatgenetics.inventory.InventoryDir(
                /* activity    => */this,
                /* requestCode => */ org.wheatgenetics.inventory.MainActivity.REQUEST_CODE);
        this.inventoryDir.createIfMissing();             // throws java.io.IOException, org.wheatge-
    }                                                    //  netics.javalib.Dir.PermissionException

    private org.wheatgenetics.inventory.SamplesTable samplesTable()
    {
        if (null == this.samplesTableInstance)
            this.samplesTableInstance = new org.wheatgenetics.inventory.SamplesTable(this);
        return this.samplesTableInstance;
    }

    private void deleteAll()
    {
        this.samplesTable().deleteAll();
        assert null != this.displayFragment; this.displayFragment.refresh();
    }

    private void exportCSV()
    {
        try
        {
            this.createInventoryDirIfMissing();          // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
            assert null != this.inventoryDir;
            java.io.File file = this.inventoryDir.createNewFile(      // throws java.io.IOException,
                this.exportFileName + ".csv");               //  org.wheatgenetics.javalib-
            if (null != file)                                         //  .Dir.PermissionException
            {
                {
                    final org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords =
                        this.samplesTable().getAll();
                    assert null != inventoryRecords; file = inventoryRecords.writeCSV(file);
                }
                if (null != file)
                {
                    org.wheatgenetics.androidlibrary.Utils.shareFile(
                        this, this.inventoryDir.parse(file));
                    this.showToast(org.wheatgenetics.inventory.R.string.exportSuccess);
                    this.deleteAll();
                }
            }
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showToast(e.getMessage());
        }
    }

    private void exportSQL()
    {
        try
        {
            this.createInventoryDirIfMissing();          // throws java.io.IOException, org.wheatge-
                                                         //  netics.javalib.Dir.PermissionException
            assert null != this.inventoryDir;
            java.io.File file = this.inventoryDir.createNewFile(      // throws java.io.IOException,
                this.exportFileName + ".sql");               //  org.wheatgenetics.javalib-
            if (null != file)                                         //  .Dir.PermissionException
            {
                {
                    final java.lang.String                                   boxList         ;
                    final org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords;
                    {
                        final org.wheatgenetics.inventory.SamplesTable samplesTable =
                            this.samplesTable();
                        assert null != samplesTable;
                        boxList          = samplesTable.getBoxList();
                        inventoryRecords = samplesTable.getAll()    ;
                    }
                    assert null != inventoryRecords;
                    file = inventoryRecords.writeSQL(file, boxList);
                }
                if (null != file)
                {
                    org.wheatgenetics.androidlibrary.Utils.shareFile(
                        this, this.inventoryDir.parse(file));
                    this.showToast(org.wheatgenetics.inventory.R.string.exportSuccess);
                    this.deleteAll();
                }
            }
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showToast(e.getMessage());
        }
    }
    // endregion

    private void displayPerson()
    {
        final android.widget.TextView personTextView = this.findViewById(
            org.wheatgenetics.inventory.R.id.personTextView);
        assert null != personTextView; assert null != this.sharedPreferences;
        personTextView.setText(this.sharedPreferences.getPerson().toString());
    }

    private void setPerson(final boolean fromMenu)
    {
        if (null == this.setPersonAlertDialog) this.setPersonAlertDialog =
            new org.wheatgenetics.inventory.SetPersonAlertDialog(this,
                new org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer()
                {
                    @java.lang.Override public void storePerson(@android.support.annotation.NonNull
                    final org.wheatgenetics.inventory.model.Person person)
                    { org.wheatgenetics.inventory.MainActivity.this.storePerson(person); }
                });

        if (fromMenu)
        {
            assert null != this.sharedPreferences;
            this.setPersonAlertDialog.show(this.sharedPreferences.getPerson());
        }
        else this.setPersonAlertDialog.show();
    }

    private void connectScale() { this.scaleReader().execute(); }

    private void getExportFileName(
    final org.wheatgenetics.inventory.MainActivity.ExportKind exportKind)
    {
        if (null == this.getExportFileNameAlertDialog) this.getExportFileNameAlertDialog =
            new org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog(
                this,this);
        this.exportKind = exportKind;
        this.getExportFileNameAlertDialog.show(
            "inventory_" + org.wheatgenetics.javalib.Utils.getDateTime());
    }

    private void clearBoxAndDeleteAll()
    {
        assert null != this.dataEntryFragment; this.dataEntryFragment.clearBox();
        this.deleteAll(); this.showToast(org.wheatgenetics.inventory.R.string.data_deleted);
    }

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* activity               => */this,
                /* changeLogRawResourceId => */
                    org.wheatgenetics.inventory.R.raw.changelog_releases);
        this.changeLogAlertDialog.show();
    }

    private void export()
    {
        if (org.wheatgenetics.inventory.MainActivity.ExportKind.CSV == this.exportKind)
            this.exportCSV();
        else
            this.exportSQL();
    }
    // endregion

    // region Overridden Methods
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.inventory.R.layout.activity_main);

        this.drawerLayout = this.findViewById(
            org.wheatgenetics.inventory.R.id.drawer_layout);       // From layout/activity_main.xml.

        // region Configure action bar.
        {
            final android.support.v7.widget.Toolbar toolbar = this.findViewById(
                org.wheatgenetics.inventory.R.id.toolbar);
            this.setSupportActionBar(toolbar);

            {
                final android.support.v7.app.ActionBar supportActionBar =
                    this.getSupportActionBar();
                if (null != supportActionBar) supportActionBar.setTitle(null);
            }

            final android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle =
                new android.support.v7.app.ActionBarDrawerToggle(this, this.drawerLayout,
                    toolbar                                                     ,
                    org.wheatgenetics.inventory.R.string.navigation_drawer_open ,
                    org.wheatgenetics.inventory.R.string.navigation_drawer_close)
                    {
                        @java.lang.Override public void onDrawerOpened(
                        final android.view.View drawerView)
                        { org.wheatgenetics.inventory.MainActivity.this.displayPerson(); }
                    };
            assert null != this.drawerLayout;
            this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }
        // endregion

        {
            // region Set version, part 1.
            // Before there was one region called "Set version.".  It consisted of the current part
            // 1 and part 2 regions concatenated together and located where part 2 is now.  Why did
            // I turn one region into two?  So that versionName would be set earlier.  Why should
            // versionName be set earlier?  So that its value could be passed to
            // NavigationItemSelectedListener() in the "Configure navigation menu." region, below.
            int versionCode; java.lang.String versionName;
            try
            {
                final android.content.pm.PackageInfo packageInfo =
                    this.getPackageManager().getPackageInfo(
                        this.getPackageName(), /* flags => */0);
                assert null != packageInfo;
                versionCode = packageInfo.versionCode; versionName = packageInfo.versionName;
            }
            catch (final android.content.pm.PackageManager.NameNotFoundException e)
            { versionCode = 0; versionName = org.wheatgenetics.javalib.Utils.adjust(null); }
            // endregion

            // region Configure navigation menu.
            {
                final android.support.design.widget.NavigationView navigationView =
                    this.findViewById(org.wheatgenetics.inventory.R.id.nav_view);   // From layout/-
                assert null != navigationView;                                      //  activity_-
                navigationView.setNavigationItemSelectedListener(                   //  main.xml.
                    new org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener(
                        /* activity    => */ this       ,
                        /* versionName => */ versionName,
                        /* handler     => */ new org.wheatgenetics.inventory.navigation
                            .NavigationItemSelectedListener.Handler()
                            {
                                @java.lang.Override public void setPerson()
                                {
                                    org.wheatgenetics.inventory.MainActivity.this.setPerson(
                                        /* fromMenu => */ true);
                                }

                                @java.lang.Override public void connectScale()
                                { org.wheatgenetics.inventory.MainActivity.this.connectScale(); }

                                @java.lang.Override public void exportCSV()
                                {
                                    org.wheatgenetics.inventory.MainActivity.this.getExportFileName(
                                        org.wheatgenetics.inventory.MainActivity.ExportKind.CSV);
                                }

                                @java.lang.Override public void exportSQL()
                                {
                                    org.wheatgenetics.inventory.MainActivity.this.getExportFileName(
                                        org.wheatgenetics.inventory.MainActivity.ExportKind.SQL);
                                }

                                @java.lang.Override public void delete()
                                {
                                    org.wheatgenetics.inventory.MainActivity
                                        .this.clearBoxAndDeleteAll();
                                }

                                @java.lang.Override public void closeDrawer()
                                { org.wheatgenetics.inventory.MainActivity.this.closeDrawer(); }
                            },
                        /* versionOnClickListener => */ new android.view.View.OnClickListener()
                            {
                                @java.lang.Override public void onClick(final android.view.View v)
                                { org.wheatgenetics.inventory.MainActivity.this.showChangeLog(); }
                            }));
            }
            // endregion

            // region Set person.
            this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
                this.getSharedPreferences("Settings", /* mode => */0));
            if (!this.sharedPreferences.personIsSet()) this.setPerson(/* fromMenu => */ false);
            // endregion

            // region Connect scale.
            if (!this.sharedPreferences.getIgnoreScale()) this.connectScale();
            // endregion

            // region Set version, part 2.
            if (!this.sharedPreferences.updateVersionIsSet(versionCode))
                { this.sharedPreferences.setUpdateVersion(versionCode); this.showChangeLog(); }
            // endregion
        }

        // region Configure fragments.
        {
            this.box = null == savedInstanceState ? "" :
                savedInstanceState.getString(org.wheatgenetics.inventory.MainActivity.BOX);

            final android.support.v4.app.FragmentManager fragmentManager =
                this.getSupportFragmentManager();
            assert null != fragmentManager;

            this.dataEntryFragment = (org.wheatgenetics.inventory.dataentry.DataEntryFragment)
                fragmentManager.findFragmentById(
                    org.wheatgenetics.inventory.R.id.dataEntryFragment);
            this.displayFragment = (org.wheatgenetics.inventory.display.DisplayFragment)
                fragmentManager.findFragmentById(org.wheatgenetics.inventory.R.id.displayFragment);
        }
        // endregion
    }

    @java.lang.Override public void onBackPressed()
    {
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            this.closeDrawer();
        else
            super.onBackPressed();
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        new android.view.MenuInflater(this).inflate(
            org.wheatgenetics.androidlibrary.R.menu.camera_options_menu, menu);
        return true;
    }

    @java.lang.Override public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        // Handle action bar item clicks here.  The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        assert null != item; final int itemId = item.getItemId();

        if (org.wheatgenetics.androidlibrary.R.id.cameraOptionsMenuItem == itemId)
        {
            if (null == this.barcodeScanner)
                this.barcodeScanner = new org.wheatgenetics.zxing.BarcodeScanner(this);
            this.barcodeScanner.scan(); return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @java.lang.Override protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        assert null != this.dataEntryFragment; this.dataEntryFragment.setEnvId(
        org.wheatgenetics.javalib.Utils.replaceIfNull(
            org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                requestCode, resultCode, data),
            "null"));
    }

    @java.lang.Override public void onRequestPermissionsResult(
                                        final int              requestCode   ,
    @android.support.annotation.NonNull final java.lang.String permissions [],
    @android.support.annotation.NonNull final int              grantResults[])
    {
        if (org.wheatgenetics.inventory.MainActivity.REQUEST_CODE == requestCode)
        {
            boolean permissionFound = false;
            for (final java.lang.String permission: permissions)
                if (android.Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission))
                    { permissionFound = true; break; }

            if (permissionFound) for (final int grantResult: grantResults)
                if (android.content.pm.PackageManager.PERMISSION_GRANTED == grantResult)
                    { this.export(); break; }
        }
    }

    @java.lang.Override protected void onSaveInstanceState(final android.os.Bundle outState)
    {
        assert null != outState;
        outState.putString(org.wheatgenetics.inventory.MainActivity.BOX, this.box);

        super.onSaveInstanceState(outState);
    }

    // region org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override public void setBox(final java.lang.String box) { this.box = box ; }
    @java.lang.Override public java.lang.String getBox()               { return this.box; }

    @java.lang.Override public void addRecord(
    final java.lang.String envid, final java.lang.String wt)
    {
        assert this.sharedPreferences != null; assert null != this.displayFragment;
        final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord =
            new org.wheatgenetics.inventory.model.InventoryRecord(
                /* box      => */ this.box                            ,
                /* envid    => */ envid                               ,
                /* person   => */ this.sharedPreferences.getSafeName(),
                /* position => */this.displayFragment.getPosition() + 1,
                /* wt       => */ wt);
        this.samplesTable().add(inventoryRecord);
        this.displayFragment.addTableRow(inventoryRecord);
    }
    // endregion

    // region org.wheatgenetics.inventory.display.DisplayFragment.Handler Overridden Methods
    @java.lang.Override public void focusEnvIdEditText()
    { assert null != this.dataEntryFragment; this.dataEntryFragment.focusEnvIdEditText(); }

    @java.lang.Override public org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords()
    { return this.samplesTable().getAll(); }

    @java.lang.Override public boolean deleteRecord(
    final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    {
        final boolean result = this.samplesTable().delete(inventoryRecord);
        if (result) { assert null != this.displayFragment; this.displayFragment.refresh(); }
        return result;
    }
    // endregion

    // region org.wheatgenetics.androidlibrary.GetExportFileNameAlertDialog.Handler Overridden Method
    @java.lang.Override public void handleGetFileNameDone(final java.lang.String fileName)
    { this.exportFileName = fileName; this.export(); }
    // endregion
    // endregion

    public void handleSetBoxButtonClick(final android.view.View v)
    { assert null != this.dataEntryFragment; dataEntryFragment.handleSetBoxButtonClick(this.box); }
}