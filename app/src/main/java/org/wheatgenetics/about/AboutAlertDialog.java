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
 * org.wheatgenetics.about.OtherApps.Index
 * org.wheatgenetics.about.OtherAppsOnClickListener
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 * org.wheatgenetics.inventory.R
 */

public class AboutAlertDialog extends java.lang.Object
{
    // region Fields
    private final android.content.Context           context               ;
    private final java.lang.String                  title, versionName    ;
    private final android.view.View.OnClickListener versionOnClickListener;

    private android.app.AlertDialog.Builder builder     = null;
    private android.app.AlertDialog         alertDialog = null;
    // endregion

    // region Public Methods
    public AboutAlertDialog(
    @android.support.annotation.NonNull final android.content.Context context,
    @android.support.annotation.NonNull final java.lang.String title         ,
    @android.support.annotation.NonNull final java.lang.String versionName   ,
    @android.support.annotation.NonNull
        final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        assert null != context;
        this.context = context;

        assert null != title;
        this.title = title;

        assert null != versionName;
        this.versionName = versionName;

        assert null != versionOnClickListener;
        this.versionOnClickListener = versionOnClickListener;
    }

    public void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                this.builder = new android.app.AlertDialog.Builder(this.context);
                {
                    final android.view.View aboutView =
                        android.view.LayoutInflater.from(this.context).inflate(
                            org.wheatgenetics.inventory.R.layout.about_alert_dialog            ,
                            /* root         => */ new android.widget.LinearLayout(this.context),
                            /* attachToRoot => */ false                                        );
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
                        otherAppsTextView.setOnClickListener(
                            new org.wheatgenetics.about.OtherAppsOnClickListener(
                                this.context, org.wheatgenetics.about.OtherApps.Index.INVENTORY));
                    }
                    builder.setCancelable(true)
                        .setTitle(this.title)
                        .setView (aboutView );
                }
                this.builder.setNegativeButton(
                    org.wheatgenetics.androidlibrary.R.string.okButtonText            ,
                    org.wheatgenetics.androidlibrary.Utils.dismissingOnClickListener());
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
    // endregion
}