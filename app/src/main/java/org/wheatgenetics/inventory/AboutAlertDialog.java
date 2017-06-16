package org.wheatgenetics.inventory;

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
 * org.wheatgenetics.inventory.R
 */

class AboutAlertDialog extends java.lang.Object
{
    private final android.content.Context           context               ;
    private final java.lang.String                  title, versionName    ;
    private final android.view.View.OnClickListener versionOnClickListener;

    private android.app.AlertDialog alertDialog = null;

    AboutAlertDialog(
    @android.support.annotation.NonNull final android.content.Context context,
    @android.support.annotation.NonNull final java.lang.String title         ,
    @android.support.annotation.NonNull final java.lang.String versionName   ,
    @android.support.annotation.NonNull
        final android.view.View.OnClickListener versionOnClickListener)
    {
        super();

        this.context                = context              ;
        this.title                  = title                ;
        this.versionName            = versionName           ;
        this.versionOnClickListener = versionOnClickListener;
    }

    void show()
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
                                public void onClick(final android.view.View v) {}
                            });
                    }
                    builder.setCancelable(true)
                        .setTitle(this.title)
                        .setView (aboutView );
                }
                this.alertDialog = builder.create();
            }
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}