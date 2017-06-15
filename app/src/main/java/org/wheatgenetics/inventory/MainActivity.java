package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Intent
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
 * android.widget.TextView
 * android.widget.Toast
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.changelog.ChangeLogAlertDialog
 * org.wheatgenetics.inventory.NavigationItemSelectedListener
 * org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser
 * org.wheatgenetics.inventory.R
 * org.wheatgenetics.inventory.SetPersonAlertDialog
 * org.wheatgenetics.inventory.SetPersonAlertDialog.PersonStorer
 * org.wheatgenetics.sharedpreferences.SharedPreferences
 * org.wheatgenetics.zxing.BarcodeScanner
 */

public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout = null;

    private org.wheatgenetics.sharedpreferences.SharedPreferences sharedPreferences          ;
    private org.wheatgenetics.changelog.ChangeLogAlertDialog      changeLogAlertDialog = null;
    private org.wheatgenetics.zxing.BarcodeScanner                barcodeScanner       = null;

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
            assert null != this.drawerLayout;
            this.drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
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
                    new org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser()
                    {
                        @java.lang.Override
                        public void closeDrawer()
                        { org.wheatgenetics.inventory.MainActivity.this.closeDrawer(); }
                    },
                    new org.wheatgenetics.inventory.NavigationItemSelectedListener.PersonSetter()
                    {
                        @java.lang.Override
                        public void setPerson()
                        { org.wheatgenetics.inventory.MainActivity.this.setPerson(); }
                    }));
        }
        // endregion

        // region Set person.
        this.sharedPreferences = new org.wheatgenetics.sharedpreferences.SharedPreferences(
            this.getSharedPreferences("Settings", 0));
        if (!this.sharedPreferences.personIsSet()) this.setPerson();
        // endregion

        // region Set version.
        int versionCode;
        try
        {
            versionCode = this.getPackageManager().getPackageInfo(
                this.getPackageName(), 0).versionCode;
        }
        catch (final android.content.pm.PackageManager.NameNotFoundException e) { versionCode = 0; }

        if (!this.sharedPreferences.updateVersionIsSet(versionCode))
        {
            this.sharedPreferences.setUpdateVersion(versionCode);
            if (null == this.changeLogAlertDialog) this.changeLogAlertDialog =
                new org.wheatgenetics.changelog.ChangeLogAlertDialog(
                    /* context                => */ this,
                    /* changeLogRawResourceId => */
                        org.wheatgenetics.inventory.R.raw.changelog_releases);
            try { this.changeLogAlertDialog.show(); } catch (final java.io.IOException e) {}
        }
        // endregion
    }

    @java.lang.Override
    public void onBackPressed()
    {
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
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
        assert null != item;
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (org.wheatgenetics.androidlibrary.R.id.cameraOptionsMenuItem == id)
        {
            if (null == this.barcodeScanner)
                this.barcodeScanner = new org.wheatgenetics.zxing.BarcodeScanner(this);
            this.barcodeScanner.scan();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @java.lang.Override
    protected void onActivityResult(final int requestCode,
    final int resultCode, final android.content.Intent data)
    {
        java.lang.String barcode = org.wheatgenetics.zxing.BarcodeScanner.parseActivityResult(
            requestCode, resultCode, data);
        if (null == barcode) barcode = "null";

        final android.widget.TextView textView =
            (android.widget.TextView) this.findViewById(org.wheatgenetics.inventory.R.id.textView);
        assert null != textView;
        textView.setText(barcode);
    }
    // endregion

    // region Private Methods
    private void showToast(final java.lang.CharSequence text)
    { android.widget.Toast.makeText(this, text, android.widget.Toast.LENGTH_SHORT).show(); }

    private void storePerson(@android.support.annotation.NonNull
    final org.wheatgenetics.inventory.model.Person person)
    {
        assert null != this.sharedPreferences;
        this.sharedPreferences.setPerson(person);
        this.showToast(
            this.getResources().getString(org.wheatgenetics.inventory.R.string.setPersonMsg) +
                person.toString()                                                               );
    }

    private void displayPerson()
    {
        final android.widget.TextView personTextView = (android.widget.TextView)
            this.findViewById(org.wheatgenetics.inventory.R.id.personTextView);
        assert null != this.sharedPreferences;
        assert null != personTextView        ;
        personTextView.setText(this.sharedPreferences.getPerson().toString());
    }

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    private void setPerson()
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
        this.setPersonAlertDialog.show();
    }
    // endregion
}