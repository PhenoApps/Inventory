package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.view.MenuItem
 *
 * org.wheatgenetics.inventory.AboutAlertDialog
 * org.wheatgenetics.inventory.R
 */

class NavigationItemSelectedListener extends java.lang.Object
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    interface DrawerCloser { void closeDrawer(); }  // TODO: Consider combining into one interface.
    interface PersonSetter { void setPerson  (); }  // TODO: Consider combining into one interface.

    private final android.content.Context context                           ;
    private final java.lang.String        aboutAlertDialogTitle, versionName;
    private final org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser
        drawerCloser;
    private final org.wheatgenetics.inventory.NavigationItemSelectedListener.PersonSetter
        personSetter;

    private org.wheatgenetics.inventory.AboutAlertDialog aboutAlertDialog = null;

    NavigationItemSelectedListener(
    @android.support.annotation.NonNull final android.content.Context context              ,
    @android.support.annotation.NonNull final java.lang.String        aboutAlertDialogTitle,
    @android.support.annotation.NonNull final java.lang.String        versionName          ,
    @android.support.annotation.NonNull
        final org.wheatgenetics.inventory.NavigationItemSelectedListener.DrawerCloser drawerCloser,
    @android.support.annotation.NonNull
        final org.wheatgenetics.inventory.NavigationItemSelectedListener.PersonSetter personSetter)
    {
        super();

        this.context               = context              ;
        this.aboutAlertDialogTitle = aboutAlertDialogTitle;
        this.versionName           = versionName          ;
        this.drawerCloser          = drawerCloser         ;
        this.personSetter          = personSetter         ;
    }

    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            // The following five ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.inventory.R.id.nav_set_person :
                this.personSetter.setPerson(); break;

            case org.wheatgenetics.inventory.R.id.nav_connect_scale : break;
            case org.wheatgenetics.inventory.R.id.nav_export        : break;
            case org.wheatgenetics.inventory.R.id.nav_delete        : break;

            case org.wheatgenetics.inventory.R.id.nav_show_about :
                if (null == this.aboutAlertDialog)
                    this.aboutAlertDialog = new org.wheatgenetics.inventory.AboutAlertDialog(
                        this.context, this.aboutAlertDialogTitle, this.versionName);
                this.aboutAlertDialog.show(); break;
        }

        this.drawerCloser.closeDrawer();
        return true;
    }
}