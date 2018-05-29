package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.os.Bundle
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.support.v4.view.GravityCompat
 * android.support.v4.widget.DrawerLayout
 * android.support.v7.app.ActionBarDrawerToggle
 * android.support.v7.app.AppCompatActivity
 * android.support.v7.widget.Toolbar
 * android.view.Menu
 * android.view.MenuItem
 *
 * org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler
 *
 * org.wheatgenetics.inventory.display.DisplayFragment.Handler
 *
 * org.wheatgenetics.inventory.R
 */
public class MainActivity extends android.support.v7.app.AppCompatActivity implements
android.support.design.widget.NavigationView.OnNavigationItemSelectedListener,
org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler,
org.wheatgenetics.inventory.display.DisplayFragment.Handler
{
    private android.support.v4.widget.DrawerLayout drawerLayout = null;

    private void closeDrawer()
    {
        assert null != this.drawerLayout;
        this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
    }

    // region Overridden Methods
    @java.lang.Override protected void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(org.wheatgenetics.inventory.R.layout.activity_main);

        this.drawerLayout = this.findViewById(
            org.wheatgenetics.inventory.R.id.drawer_layout);       // From layout/activity_main.xml.

        {
            final android.support.v7.widget.Toolbar toolbar =
                this.findViewById(org.wheatgenetics.inventory.R.id.toolbar);
            this.setSupportActionBar(toolbar);

            final android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle =
                new android.support.v7.app.ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                    org.wheatgenetics.inventory.R.string.navigation_drawer_open ,
                    org.wheatgenetics.inventory.R.string.navigation_drawer_close);
            assert null != this.drawerLayout;
            this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }

        final android.support.design.widget.NavigationView navigationView =
            this.findViewById(org.wheatgenetics.inventory.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @java.lang.Override public void onBackPressed()
    {
        assert null != this.drawerLayout;
        if (this.drawerLayout.isDrawerOpen(android.support.v4.view.GravityCompat.START))
            this.drawerLayout.closeDrawer(android.support.v4.view.GravityCompat.START);
        else
            super.onBackPressed();
    }

    @java.lang.Override public boolean onCreateOptionsMenu(final android.view.Menu menu)
    { return true; }

    @java.lang.Override public boolean onOptionsItemSelected(final android.view.MenuItem item)
    { return super.onOptionsItemSelected(item); }

    @java.lang.SuppressWarnings({"StatementWithEmptyBody"}) @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    { this.closeDrawer(); return true; }

    // region org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler Overridden Methods
    @java.lang.Override public void setBox(final java.lang.String box) { /*this.box = box;*/ }
    @java.lang.Override public java.lang.String getBox() { return null/*this.box*/; }

    @java.lang.Override public void addRecord(
    final java.lang.String envid, final java.lang.String wt)
    {
//        assert this.sharedPreferences != null; assert null != this.displayFragment;
//        final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord =
//            new org.wheatgenetics.inventory.model.InventoryRecord(
//                /* box      => */ this.box                              ,
//                /* envid    => */ envid                                 ,
//                /* person   => */ this.sharedPreferences.getSafeName()  ,
//                /* position => */ this.displayFragment.getPosition() + 1,
//                /* wt       => */ wt                                    );
//        this.samplesTable().add(inventoryRecord);
//        this.displayFragment.addTableRow(inventoryRecord);
    }
    // endregion

    // region org.wheatgenetics.inventory.display.DisplayFragment.Handler Overridden Methods
    @java.lang.Override public void focusEnvIdEditText()
    { /*assert null != this.dataEntryFragment; this.dataEntryFragment.focusEnvIdEditText();*/ }

    @java.lang.Override
    public org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords()
    { return null/*this.samplesTable().getAll()*/; }

    @java.lang.Override public boolean deleteRecord(
    final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    {
        /*final boolean result = this.samplesTable().delete(inventoryRecord);
        if (result) { assert null != this.displayFragment; this.displayFragment.refresh(); }*/
        return false/*result*/;
    }
    // endregion
    // endregion
}