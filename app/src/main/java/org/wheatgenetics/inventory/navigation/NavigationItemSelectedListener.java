package org.wheatgenetics.inventory.navigation;

/**
 * Uses:
 * android.content.Context
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
public class NavigationItemSelectedListener extends java.lang.Object
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    public interface Handler
    {
        public abstract void setPerson  (); public abstract void connectScale();
        public abstract void closeDrawer();
    }

    // region Fields
    private final android.content.Context context              ;
    private final int                     aboutAlertDialogTitle;
    private final java.lang.String        versionName          ;
    private final org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener.Handler
        navigationHandler;
    private final org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler exportHandler;
    private final org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler deleteHandler;
    private final android.view.View.OnClickListener                       versionOnClickListener;

    private org.wheatgenetics.inventory.navigation.ExportAlertDialog exportAlertDialog = null;
    private org.wheatgenetics.inventory.navigation.DeleteAlertDialog deleteAlertDialog = null;
    private org.wheatgenetics.about.AboutAlertDialog                 aboutAlertDialog  = null;
    // endregion

    public NavigationItemSelectedListener(final android.content.Context context,
    final int aboutAlertDialogTitle, final java.lang.String versionName,
    final org.wheatgenetics.inventory.navigation.NavigationItemSelectedListener.Handler
        navigationHandler,
    final org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler exportHandler         ,
    final org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler deleteHandler         ,
    final android.view.View.OnClickListener                                versionOnClickListener)
    {
        super();

        this.context                = context               ;
        this.aboutAlertDialogTitle  = aboutAlertDialogTitle ;
        this.versionName            = versionName           ;
        this.navigationHandler      = navigationHandler     ;
        this.exportHandler          = exportHandler         ;
        this.deleteHandler          = deleteHandler         ;
        this.versionOnClickListener = versionOnClickListener;
    }

    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        assert null != item; assert null != this.navigationHandler;
        switch (item.getItemId())
        {
            // The following five ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.inventory.R.id.nav_set_person :
                this.navigationHandler.setPerson(); break;

            case org.wheatgenetics.inventory.R.id.nav_connect_scale :
                this.navigationHandler.connectScale(); break;

            case org.wheatgenetics.inventory.R.id.nav_export :
                if (null == this.exportAlertDialog) this.exportAlertDialog =
                    new org.wheatgenetics.inventory.navigation.ExportAlertDialog(
                        this.context, this.exportHandler);
                this.exportAlertDialog.show(); break;

            case org.wheatgenetics.inventory.R.id.nav_delete :
                if (null == this.deleteAlertDialog) this.deleteAlertDialog =
                    new org.wheatgenetics.inventory.navigation.DeleteAlertDialog(
                        this.context, this.deleteHandler);
                this.deleteAlertDialog.show(); break;

            case org.wheatgenetics.inventory.R.id.nav_show_about :
                if (null == this.aboutAlertDialog)
                {
                    assert null != this.context;
                    this.aboutAlertDialog = new org.wheatgenetics.about.AboutAlertDialog(
                        this.context, this.context.getString(this.aboutAlertDialogTitle),
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

        this.navigationHandler.closeDrawer();
        return true;
    }
}