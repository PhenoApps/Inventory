package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Context
 * android.support.annotation.NonNull
 * android.support.design.widget.NavigationView
 * android.view.MenuItem
 * android.view.View.OnClickListener
 *
 * org.wheatgenetics.about.AboutAlertDialog
 * org.wheatgenetics.inventory.R
 */

class NavigationItemSelectedListener extends java.lang.Object
implements android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
{
    interface Handler
    {
        public abstract void setPerson  ();
        public abstract void closeDrawer();
    }

    private final android.content.Context                                            context;
    private final java.lang.String                        aboutAlertDialogTitle, versionName;
    private final org.wheatgenetics.inventory.NavigationItemSelectedListener.Handler handler;
    private final android.view.View.OnClickListener                   versionOnClickListener;

    private org.wheatgenetics.about.AboutAlertDialog aboutAlertDialog = null;

    NavigationItemSelectedListener(
    @android.support.annotation.NonNull final android.content.Context context              ,
    @android.support.annotation.NonNull final java.lang.String        aboutAlertDialogTitle,
    @android.support.annotation.NonNull final java.lang.String        versionName          ,
    @android.support.annotation.NonNull
        final org.wheatgenetics.inventory.NavigationItemSelectedListener.Handler handler,
    @android.support.annotation.NonNull
        final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        assert null != context;
        this.context = context;

        assert null != aboutAlertDialogTitle;
        this.aboutAlertDialogTitle  = aboutAlertDialogTitle;

        assert null != versionName;
        this.versionName = versionName;

        assert null != handler;
        this.handler = handler;

        assert null != versionOnClickListener;
        this.versionOnClickListener = versionOnClickListener;
    }

    @java.lang.Override
    public boolean onNavigationItemSelected(
    @android.support.annotation.NonNull final android.view.MenuItem item)
    {
        // Handle navigation view item clicks here.
        assert null != item        ;
        assert null != this.handler;
        switch (item.getItemId())
        {
            // The following five ids that have names that start with "nav_" come from
            // menu/activity_main_drawer.xml.
            case org.wheatgenetics.inventory.R.id.nav_set_person :
                this.handler.setPerson(); break;

            case org.wheatgenetics.inventory.R.id.nav_connect_scale : break;
            case org.wheatgenetics.inventory.R.id.nav_export        : break;
            case org.wheatgenetics.inventory.R.id.nav_delete        : break;

            case org.wheatgenetics.inventory.R.id.nav_show_about :
                if (null == this.aboutAlertDialog) this.aboutAlertDialog =
                    new org.wheatgenetics.about.AboutAlertDialog(this.context,
                        this.aboutAlertDialogTitle, this.versionName, this.versionOnClickListener);
                this.aboutAlertDialog.show(); break;
        }

        this.handler.closeDrawer();
        return true;
    }
}