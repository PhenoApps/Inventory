package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.AlertDialog
 * android.content.DialogInterface
 * android.content.Context
 */

class ChangeLogAlertDialog extends java.lang.Object
{
    // region Protected Fields
    protected android.content.Context   context            = null;
    protected android.content.Context   applicationContext = null;
    protected java.io.InputStreamReader inputStreamReader  = null;

    protected org.wheatgenetics.inventory.ChangeLogScrollView changeLogScrollView = null;


    protected android.content.Context activityClass      = null;
    protected java.lang.CharSequence  title              = null;
    protected java.lang.CharSequence  positiveButtonText = null;

    protected android.app.AlertDialog.Builder builder = null;


    protected android.app.AlertDialog alertDialog = null;
    // endregion


    ChangeLogAlertDialog(final android.content.Context context,
    final android.content.Context applicationContext,
    final java.io.InputStreamReader inputStreamReader, final android.content.Context activityClass,
    final java.lang.CharSequence title, final java.lang.CharSequence positiveButtonText)
    {
        super();


        // For changeLogScrollView:
        assert context != null;
        this.context = context;

        assert applicationContext != null;
        this.applicationContext = applicationContext;

        assert inputStreamReader != null;
        this.inputStreamReader = inputStreamReader;


        // For builder:
        assert activityClass != null;
        this.activityClass = activityClass;

        assert title != null;
        this.title = title;

        assert positiveButtonText != null;
        this.positiveButtonText = positiveButtonText;
    }

    void show() throws java.io.IOException
    {
        if (this.alertDialog == null)
        {
            if (this.builder == null)
            {
                if (this.changeLogScrollView == null)
                    this.changeLogScrollView = new org.wheatgenetics.inventory.ChangeLogScrollView(
                        this.context, this.applicationContext, this.inputStreamReader);

                this.builder = new android.app.AlertDialog.Builder(this.activityClass);
                this.builder.setTitle(this.title)
                    .setView(this.changeLogScrollView.get())           // throws java.io.IOException
                    .setCancelable(true)
                    .setPositiveButton(this.positiveButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(android.content.DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        });
            }
            this.alertDialog = this.builder.create();
        }
        this.alertDialog.show();
    }
}