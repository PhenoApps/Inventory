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

        this.drawerCloser.closeDrawer();
        return true;
    }
}