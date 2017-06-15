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
    interface PersonSetter { void setPerson  (); }

    private final org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser
        drawerCloser;
    private final org.wheatgenetics.inventory.NavigationItemSelectedListener.PersonSetter
        personSetter;

    NavigationItemSelectedListener(@android.support.annotation.NonNull
    final org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser drawerCloser,
    @android.support.annotation.NonNull
    final org.wheatgenetics.inventory.NavigationItemSelectedListener.PersonSetter personSetter)
    {
        super();

        this.drawerCloser = drawerCloser;
        this.personSetter = personSetter;
    }

    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case org.wheatgenetics.inventory.R.id.nav_set_person :           // From menu/activity_-
                this.personSetter.setPerson();                               //  main_drawer.xml.
                break;
            case org.wheatgenetics.inventory.R.id.nav_connect_scale : break;        // From menu/ac-
            case org.wheatgenetics.inventory.R.id.nav_export        : break;        //  tivity_main-
            case org.wheatgenetics.inventory.R.id.nav_delete        : break;        //  _drawer
            case org.wheatgenetics.inventory.R.id.nav_show_about    : break;        //  .xml.
        }

        this.drawerCloser.closeDrawer();
        return true;
    }
}