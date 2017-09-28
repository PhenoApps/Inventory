package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.pm.PackageInfo
 * android.content.pm.PackageManager.NameNotFoundException
 * android.os.Bundle
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBar
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuItem
 * android.view.View
 * android.view.View.OnClickListener
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler
 * org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler
 * org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener
 *
 * org.wheatgenetics.inventory.R
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity
{
    // region Fields
    private android.support.v4.widget.DrawerLayout drawerLayout = null;
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
                    { /* TODO: org.wheatgenetics.inventory.MainActivity.this.displayPerson(); */ }
                };
            assert null != this.drawerLayout; this.drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
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
                        this.getPackageName(), /* flags => */ 0);
                assert null != packageInfo;
                versionCode = packageInfo.versionCode; versionName = packageInfo.versionName;
            }
            catch (final android.content.pm.PackageManager.NameNotFoundException e)
            { versionCode = 0; versionName = org.wheatgenetics.javalib.Utils.adjust(null); }
            // endregion

            // region Configure navigation menu.
            {
                final android.support.design.widget.NavigationView navigationView =
                    (android.support.design.widget.NavigationView) this.findViewById(
                        org.wheatgenetics.inventory.R.id.nav_view);             // From layout/ac-
                assert null != navigationView;                                  //  tivity_main.xml.
                navigationView.setNavigationItemSelectedListener(
                    new org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener(
                        /* activity          => */ this       ,
                        /* versionName       => */ versionName,
                        /* navigationHandler => */ new org.wheatgenetics.inventory.navigation
                            .NavigationItemSelectedListener.Handler()
                            {
                                @java.lang.Override
                                public void setPerson()
                                {
                                    // org.wheatgenetics.inventory.MainActivity.this.setPerson(
                                    //     /* fromMenu => */ true);
                                }

                                @java.lang.Override
                                public void connectScale()
                                { /* org.wheatgenetics.inventory.MainActivity.this.connectScale(); */ }

                                @java.lang.Override
                                public void closeDrawer()
                                { org.wheatgenetics.inventory.MainActivity.this.closeDrawer(); }
                            },
                        /* exportHandler => */
                            new org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler()
                            {
                                @java.lang.Override
                                public void exportCSV()
                                { /* org.wheatgenetics.inventory.MainActivity.this.exportCSV(); */ }

                                @java.lang.Override
                                public void exportSQL()
                                { /* org.wheatgenetics.inventory.MainActivity.this.exportSQL(); */ }
                            },
                        /* deleteHandler => */
                            new org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler()
                            {
                                @java.lang.Override
                                public void delete() { /* TODO */ }
                            },
                        /* versionOnClickListener => */ new android.view.View.OnClickListener()
                            {
                                @java.lang.Override
                                public void onClick(final android.view.View v)
                                { /* org.wheatgenetics.inventory.MainActivity.this.showChangeLog(); */ }
                            }));
            }
            // endregion
        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(org.wheatgenetics.inventory.R.menu.main, menu);
        return true;
    }

    @java.lang.Override
    public boolean onOptionsItemSelected(final android.view.MenuItem item)
    {
        // Handle action bar item clicks here.  The action bar will automatically handle clicks on
        // the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        assert null != item; final int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (org.wheatgenetics.inventory.R.id.action_settings == itemId)
            return true;
        else
            return super.onOptionsItemSelected(item);
    }
    // endregion

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }
}