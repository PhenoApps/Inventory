package org.wheatgenetics.about;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.support.annotation.NonNull
 * android.view.LayoutInflater
 * android.view.View
 * android.view.View.OnClickListener
 * android.widget.LinearLayout
 * android.widget.TextView
 *
 * org.wheatgenetics.about.OtherApps
 * org.wheatgenetics.about.OtherApps.Index
 * org.wheatgenetics.about.OtherAppsAlertDialog
 * org.wheatgenetics.about.OtherAppsAlertDialog.Handler
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.inventory.R
 */

public class AboutAlertDialog extends java.lang.Object
{
    private final android.content.Context                              context               ;
    private final java.lang.String                                     title, versionName    ;
    private final android.view.View.OnClickListener                    versionOnClickListener;
    private final org.wheatgenetics.about.OtherAppsAlertDialog.Handler otherAppsHandler      ;

    private org.wheatgenetics.about.OtherAppsAlertDialog otherAppsAlertDialog = null;
    private android.app.AlertDialog                      alertDialog          = null;

    private void showOtherAppsAlertDialog()
    {
        if (null == this.otherAppsAlertDialog)
            this.otherAppsAlertDialog = new org.wheatgenetics.about.OtherAppsAlertDialog(
                this.context,
                new org.wheatgenetics.about.OtherApps(
                    org.wheatgenetics.about.OtherApps.Index.INVENTORY),
                this.otherAppsHandler);
        this.otherAppsAlertDialog.show();
    }

    public AboutAlertDialog(
    @android.support.annotation.NonNull final android.content.Context context,
    @android.support.annotation.NonNull final java.lang.String title         ,
    @android.support.annotation.NonNull final java.lang.String versionName   ,
    @android.support.annotation.NonNull
        final android.view.View.OnClickListener versionOnClickListener,
    @android.support.annotation.NonNull
        final org.wheatgenetics.about.OtherAppsAlertDialog.Handler otherAppsHandler)
    {
        super();

        this.context                = context               ;
        this.title                  = title                 ;
        this.versionName            = versionName           ;
        this.versionOnClickListener = versionOnClickListener;
        this.otherAppsHandler       = otherAppsHandler      ;
    }

    public void show()
    {
        if (null == this.alertDialog)
        {
            {
                final android.app.AlertDialog.Builder builder =
                    new android.app.AlertDialog.Builder(this.context);
                {
                    final android.view.View aboutView =
                        android.view.LayoutInflater.from(this.context).inflate(
                            org.wheatgenetics.inventory.R.layout.about_alert_dialog,
                            new android.widget.LinearLayout(this.context)          ,
                            false                                                  );
                    {
                        assert null != aboutView;
                        final android.widget.TextView versionTextView = (android.widget.TextView)
                            aboutView.findViewById(
                                org.wheatgenetics.inventory.R.id.aboutVersionTextView);
                        assert null != versionTextView;
                        versionTextView.setText(versionTextView.getText() + " " + this.versionName);
                        versionTextView.setOnClickListener(this.versionOnClickListener);
                    }
                    {
                        final android.widget.TextView otherAppsTextView = (android.widget.TextView)
                            aboutView.findViewById(
                                org.wheatgenetics.inventory.R.id.aboutOtherAppsTextView);
                        assert null != otherAppsTextView;
                        otherAppsTextView.setOnClickListener(new android.view.View.OnClickListener()
                            {
                                @java.lang.Override
                                public void onClick(final android.view.View v)
                                {
                                    org.wheatgenetics.about.
                                        AboutAlertDialog.this.showOtherAppsAlertDialog();
                                }
                            });
                    }
                    builder.setCancelable(true)
                        .setTitle(this.title)
                        .setView (aboutView );
                }
                builder.setNegativeButton(org.wheatgenetics.androidlibrary.R.string.okButtonText,
                    org.wheatgenetics.androidlibrary.Utils.dismissingOnClickListener());
                this.alertDialog = builder.create();
            }
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}