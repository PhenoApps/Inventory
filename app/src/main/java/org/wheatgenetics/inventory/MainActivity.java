package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Intent
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
 * android.os.Bundle
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * andorid.view.MenuInflater
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.TextView
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.usb.Device.Exception
 * org.wheatgenetics.usb.ScaleExceptionAlertDialog
 * org.wheatgenetics.usb.ScaleExceptionAlertDialog.Handler
 * org.wheatgenetics.usb.ScaleReader
 * org.wheatgenetics.usb.ScaleReader.Handler
 * org.wheatgenetics.zxing.BarcodeScanner
 *
 * org.wheatgenetics.inventory.model.Person
 * org.wheatgenetics.inventory.NavigationItemSelectedListener
 * org.wheatgenetics.inventory.NavigationItemSelectedListener.Handler
 * org.wheatgenetics.inventory.R
 * org.wheatgenetics.inventory.SetPersonAlertDialog
 * org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout = null;

    private android.widget.TextView textView = null;

    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences               ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog      = null;
    private org.wheatgenetics.zxing.BarcodeScanner                barcodeScanner            = null;
    private org.wheatgenetics.usb.ScaleReader                     scaleReaderInstance       = null;
    private org.wheatgenetics.usb.ScaleExceptionAlertDialog       scaleExceptionAlertDialog = null;

    private org.wheatgenetics.inventory.SetPersonAlertDialog setPersonAlertDialog = null;
    // endregion

    // region Overridden Methods
    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.inventory.R.layout.activity_main);

        this.drawerLayout = (android.support.v4.widget.DrawerLayout) this.findViewById(
            org.wheatgenetics.inventory.R.id.drawer_layout);       // From layout/activity_main.xml.

        // region Configure action bar.
        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.inventory.R.id.toolbar);     // From layout/app-
            this.setSupportActionBar(toolbar);                                   //  _bar_main.xml.

            {
                final android.support.v7.app.ActionBar supportActionBar =
                    this.getSupportActionBar();
                if (null != supportActionBar) supportActionBar.setTitle(null);
            }

            final android.support.v7.app.ActionBarDrawerToggle toggle =
                new android.support.v7.app.ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                org.wheatgenetics.inventory.R.string.navigation_drawer_open ,
                org.wheatgenetics.inventory.R.string.navigation_drawer_close)
                {
                    @java.lang.Override
                    public void onDrawerOpened(final android.view.View drawerView)
                    { org.wheatgenetics.inventory.MainActivity.this.displayPerson(); }
                };
            assert null != this.drawerLayout; this.drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        }
        // endregion

        // region Set version, part 1.
        // Before there was one region called "Set version.".  It consisted of the current part 1
        // and part 2 regions concatenated together and located where part 2 is now.  Why did I
        // turn one region into two?  So that versionName would be set earlier.  Why should
        // versionName be set earlier?  So that its value could be passed to
        // NavigationItemSelectedListener() in the "Configure navigation menu" region, below.
        int              versionCode;
        java.lang.String versionName;
        try
        {
            final android.content.pm.PackageInfo packageInfo =
                this.getPackageManager().getPackageInfo(this.getPackageName(), /* flags => */ 0);
            assert null != packageInfo;
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        }
        catch (final android.content.pm.PackageManager.NameNotFoundException e)
        {
            versionCode = 0                                           ;
            versionName = org.wheatgenetics.javalib.Utils.adjust(null);
        }
        // endregion

        // region Configure navigation menu.
        {
            final android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView) this.findViewById(
                    org.wheatgenetics.inventory.R.id.nav_view);                 // From layout/ac-
            assert null != navigationView;                                      //  tivity_main.xml.
            navigationView.setNavigationItemSelectedListener(
                new org.wheatgenetics.inventory.NavigationItemSelectedListener(
                    /* context               => */ this,
                    /* aboutAlertDialogTitle => */
                        org.wheatgenetics.inventory.R.string.aboutAlertDialogTitle,
                    /* versionName => */ versionName,
                    /* handler     => */
                        new org.wheatgenetics.inventory.NavigationItemSelectedListener.Handler()
                        {
                            @java.lang.Override
                            public void setPerson()
                            {
                                org.wheatgenetics.inventory.MainActivity.this.setPerson(
                                    /* fromMenu => */ true);
                            }

                            @java.lang.Override
                            public void connectScale()
                            { org.wheatgenetics.inventory.MainActivity.this.connectScale(); }

                            @java.lang.Override
                            public void closeDrawer()
                            { org.wheatgenetics.inventory.MainActivity.this.closeDrawer(); }
                        },
                    /* versionOnClickListener => */ new android.view.View.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.view.View v)
                            { org.wheatgenetics.inventory.MainActivity.this.showChangeLog(); }
                        }));
        }
        // endregion

        // region Set person.
        this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
            this.getSharedPreferences("Settings", 0));
        if (!this.sharedPreferences.personIsSet()) this.setPerson(/* fromMenu => */ false);
        // endregion

        // region Connect scale.
        if (!this.sharedPreferences.getIgnoreScale()) this.connectScale();
        // endregion

        // region Set version, part 2.
        if (!this.sharedPreferences.updateVersionIsSet(versionCode))
        {
            this.sharedPreferences.setUpdateVersion(versionCode);
            this.showChangeLog();
        }
        // endregion
    }

    @java.lang.Override
    public void onBackPressed()
    {
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            this.closeDrawer();
        else
            super.onBackPressed();
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu)
    {
        new android.view.MenuInflater(this).inflate(
            org.wheatgenetics.androidlibrary.R.menu.camera_options_menu, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        assert null != item; final int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (org.wheatgenetics.androidlibrary.R.id.cameraOptionsMenuItem == itemId)
        {
            if (null == this.barcodeScanner)
                this.barcodeScanner = new org.wheatgenetics.zxing.BarcodeScanner(this);
            this.barcodeScanner.scan();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @java.lang.Override
    protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        this.setTextViewText(org.wheatgenetics.javalib.Utils.replaceIfNull(
            org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
                requestCode, resultCode, data),
            "null"));
    }
    // endregion

    // region Private Methods
    // region Private Private Methods
    private void showToast(final java.lang.CharSequence text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this, text); }

    private void storePerson(@android.support.annotation.NonNull
    final org.wheatgenetics.inventory.model.Person person)
    {
        assert null != this.sharedPreferences; this.sharedPreferences.setPerson(person);
        this.showToast(
            this.getString(org.wheatgenetics.inventory.R.string.setPersonMsg) + person.toString());
    }

    private void ignoreScale()
    { assert null != this.sharedPreferences; this.sharedPreferences.setIgnoreScaleToTrue(); }

    private void reportException(final org.wheatgenetics.usb.Device.Exception e)
    {
        this.scaleReader().cancel();
        if (null == this.scaleExceptionAlertDialog) this.scaleExceptionAlertDialog =
            new org.wheatgenetics.usb.ScaleExceptionAlertDialog(this,
                new org.wheatgenetics.usb.ScaleExceptionAlertDialog.Handler()
                {
                    @java.lang.Override
                    public void tryAgain()
                    { org.wheatgenetics.inventory.MainActivity.this.connectScale(); }

                    @java.lang.Override
                    public void ignore()
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
                    @java.lang.Override
                    public void publish(final java.lang.String s)
                    { org.wheatgenetics.inventory.MainActivity.this.setTextViewText(s); }

                    @java.lang.Override
                    public void reportException(final org.wheatgenetics.usb.Device.Exception e)
                    { org.wheatgenetics.inventory.MainActivity.this.reportException(e); }
                });
        return this.scaleReaderInstance;
    }
    // endregion

    private void displayPerson()
    {
        final android.widget.TextView personTextView = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.inventory.R.id.personTextView);
        assert null != personTextView; assert null != this.sharedPreferences;
        personTextView.setText(this.sharedPreferences.getPerson().toString());
    }

    private void setPerson(final boolean fromMenu)
    {
        if (null == this.setPersonAlertDialog)
            this.setPersonAlertDialog = new org.wheatgenetics.inventory.SetPersonAlertDialog(this,
                new org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer()
                {
                    @java.lang.Override
                    public void storePerson(@android.support.annotation.NonNull
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

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    private void showChangeLog()
    {
        if (null == this.changeLogAlertDialog)
            this.changeLogAlertDialog = new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                /* context                => */ this,
                /* changeLogRawResourceId => */
                    org.wheatgenetics.inventory.R.raw.changelog_releases);
        try                                 { this.changeLogAlertDialog.show()       ; }
        catch (final java.io.IOException e) { throw new java.lang.RuntimeException(e); }
    }

    private void setTextViewText(final java.lang.String text)
    {
        if (null == this.textView)
        {
            this.textView = (android.widget.TextView)
                this.findViewById(org.wheatgenetics.inventory.R.id.textView);
            assert null != this.textView;
        }
        this.textView.setText(text);
    }
    // endregion
}