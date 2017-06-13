package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.os.Bundle
 * android.support.design.widget.NavigationView
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuItem
 *
 * org.wheatgenetics.inventory.R
 */

public class MainActivity extends android.support.v7.app.AppCompatActivity
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    private android.support.v4.widget.DrawerLayout drawerLayout = null;

    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.inventory.R.layout.activity_main);

        this.drawerLayout = (android.support.v4.widget.DrawerLayout) this.findViewById(
            org.wheatgenetics.inventory.R.id.drawer_layout);       // From layout/activity_main.xml.

        {
            final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)
                this.findViewById(org.wheatgenetics.inventory.R.id.toolbar);     // From layout/app-
            this.setSupportActionBar(toolbar);                                   //  _bar_main.xml.

            {
                final android.support.v7.app.ActionBarDrawerToggle toggle = new
                    android.support.v7.app.ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                        org.wheatgenetics.inventory.R.string.navigation_drawer_open ,
                        org.wheatgenetics.inventory.R.string.navigation_drawer_close);
                assert null != this.drawerLayout;
                this.drawerLayout.setDrawerListener(toggle);
                toggle.syncState();
            }
        }

        final android.support.design.widget.NavigationView navigationView =
            (android.support.design.widget.NavigationView)
            this.findViewById(org.wheatgenetics.inventory.R.id.nav_view);       // From layout/ac-
        assert null != navigationView;                                          //  tivity_main.xml.
        navigationView.setNavigationItemSelectedListener(this);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(org.wheatgenetics.inventory.R.menu.main, menu);
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
        if (org.wheatgenetics.inventory.R.id.action_settings == id)           // From menu/main.xml.
            return true;
        else
            return super.onOptionsItemSelected(item);
    }

    /**
     * Overrides abstract method in android.support.design.widget.NavigationView.OnNavigationItemSe-
     * lectedListener.
     */
    @java.lang.Override
    public boolean onNavigationItemSelected(final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case org.wheatgenetics.inventory.R.id.nav_camera   : break;                // From
            case org.wheatgenetics.inventory.R.id.nav_gallery  : break;                //  menu/-
            case org.wheatgenetics.inventory.R.id.nav_slideshow: break;                //  activity-
            case org.wheatgenetics.inventory.R.id.nav_manage   : break;                //  _main-
            case org.wheatgenetics.inventory.R.id.nav_share    : break;                //  _drawer-
            case org.wheatgenetics.inventory.R.id.nav_send     : break;                //  .xml.
        }

        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
        return true;
    }
}