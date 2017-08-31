package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.R
 */
class DeleteAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void delete(); }

    // region Fields
    private final android.content.Context                               context;
    private final org.wheatgenetics.inventory.DeleteAlertDialog.Handler handler;

    private android.app.AlertDialog         alertDialog = null;
    private android.app.AlertDialog.Builder builder     = null;
    // endregion

    private void delete()
    { assert null != this.handler; this.handler.delete(); }

    DeleteAlertDialog(final android.content.Context context,
    final org.wheatgenetics.inventory.DeleteAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
                this.builder = new android.app.AlertDialog.Builder(this.context)
                    .setTitle     (org.wheatgenetics.inventory.R.string.deleteAlertDialogTitle)
                    .setCancelable(false                                                      )
                    .setMessage   (org.wheatgenetics.inventory.R.string.deleteAlertDialogMsg  )
                    .setPositiveButton(
                        org.wheatgenetics.inventory.R.string.deleteAlertDialogPositive,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            { org.wheatgenetics.inventory.DeleteAlertDialog.this.delete(); }
                        })
                    .setNegativeButton(
                        org.wheatgenetics.inventory.R.string.deleteAlertDialogNegative    ,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}