package org.wheatgenetics.inventory.navigation;

/**
 * Uses:
 * android.app.Activity
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * android.view.MenuItem
 * android.view.View.OnClickListener
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.about.OtherApps.Index
 *
 * org.wheatgenetics.inventory.R
 *
 * org.wheatgenetics.inventory.navigation.DeleteAlertDialog
 * org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler
 * org.wheatgenetics.inventory.navigation.ExportAlertDialog
 * org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class NavigationItemSelectedListener extends java.lang.Object implements
android.support.design.widget.NavigationView.OnNavigationItemSelectedListener,
org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler             ,
org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler
{
    @java.lang.SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface Handler
    {
        public abstract void setPerson  (); public abstract void connectScale();
        public abstract void exportCSV  (); public abstract void exportSQL   ();
        public abstract void delete     (); public abstract void closeDrawer ();
    }

    // region Fields
    private final android.app.Activity activity   ;
    private final java.lang.String     versionName;
    private final org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener.Handler
        handler;
    private final android.view.View.OnClickListener versionOnClickListener;

    private org.wheatgenetics.inventory.navigation.ExportAlertDialog exportAlertDialog = null;
    private org.wheatgenetics.inventory.navigation.DeleteAlertDialog deleteAlertDialog = null;
    private org.wheatgenetics.about.AboutAlertDialog                 aboutAlertDialog  = null;
    // endregion

    public NavigationItemSelectedListener(final android.app.Activity activity,
    final java.lang.String versionName,
    final org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener.Handler handler,
    final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        this.activity = activity; this.versionName            = versionName           ;
        this.handler  = handler ; this.versionOnClickListener = versionOnClickListener;
    }

    // region Overridden Methods
    // region android.support.design.widget.NavigationView.OnNavigationItemSelectedListener Overridden Method
    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        assert null != item; assert null != this.handler;
        switch (item.getItemId())
        {
            // The following five ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.inventory.R.id.nav_set_person : this.handler.setPerson(); break;

            case org.wheatgenetics.inventory.R.id.nav_connect_scale :
                this.handler.connectScale(); break;

            case org.wheatgenetics.inventory.R.id.nav_export :
                if (null == this.exportAlertDialog) this.exportAlertDialog =
                    new org.wheatgenetics.inventory.navigation.ExportAlertDialog(
                        this.activity, this);
                this.exportAlertDialog.show(); break;

            case org.wheatgenetics.inventory.R.id.nav_delete :
                if (null == this.deleteAlertDialog) this.deleteAlertDialog =
                    new org.wheatgenetics.inventory.navigation.DeleteAlertDialog(
                        this.activity, this);
                this.deleteAlertDialog.show(); break;

            case org.wheatgenetics.inventory.R.id.nav_show_about :
                if (null == this.aboutAlertDialog)
                {
                    assert null != this.activity;
                    this.aboutAlertDialog = new org.wheatgenetics.about.AboutAlertDialog(
                        this.activity,
                        this.activity.getString(
                            org.wheatgenetics.inventory.R.string.aboutAlertDialogTitle),
                        this.versionName, new java.lang.String[] {
                            "\nInventory is a free and open source application that can be used t" +
                                "o catalog individuals while also collecting and storing sample w" +
                                "eight data. The app was designed to work with Elane USB Plus 5kg" +
                                " Scales (http://www.elane.net).\n",
                            "Inventory uses code from the following open source projects:",
                            "theUltimateScale ( https://github.com/" +
                                "theUltimateLabs/theUtimateScale )" },
                        org.wheatgenetics.about.OtherApps.Index.INVENTORY,
                        this.versionOnClickListener                      );
                }
                this.aboutAlertDialog.show(); break;
        }

        this.handler.closeDrawer(); return true;
    }
    // endregion

    // region org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler Overridden Methods
    @java.lang.Override
    public void exportCSV() { assert null != this.handler; this.handler.exportCSV(); }

    @java.lang.Override
    public void exportSQL() { assert null != this.handler; this.handler.exportSQL(); }
    // endregion

    // region org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void delete() { assert null != this.handler; this.handler.delete(); }
    // endregion
    // endregion
}