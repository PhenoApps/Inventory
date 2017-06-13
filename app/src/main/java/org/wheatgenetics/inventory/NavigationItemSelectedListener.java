package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.view.MenuItem
 *
 * org.wheatgenetics.inventory.R
 */

class NavigationItemSelectedListener extends java.lang.Object
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    interface DrawerCloser { void closeDrawer(); }

    private final org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser
        drawerCloser;

    NavigationItemSelectedListener(@android.support.annotation.NonNull
    final org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser drawerCloser)
    {
        super();
        this.drawerCloser = drawerCloser;
    }

    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case org.wheatgenetics.inventory.R.id.nav_set_person    : break;          // From menu/-
            case org.wheatgenetics.inventory.R.id.nav_connect_scale : break;          //  activity-
            case org.wheatgenetics.inventory.R.id.nav_export        : break;          //  _main-
            case org.wheatgenetics.inventory.R.id.nav_delete        : break;          //  _drawer
            case org.wheatgenetics.inventory.R.id.nav_show_about    : break;          //  .xml.
        }

        this.drawerCloser.closeDrawer();
        return true;
    }
}