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
class ExportAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void exportCSV(); public abstract void exportSQL(); }

    // region Fields
    private final android.content.Context                               context;
    private final org.wheatgenetics.inventory.ExportAlertDialog.Handler handler;

    private android.app.AlertDialog         alertDialog = null;
    private android.app.AlertDialog.Builder builder     = null;
    // endregion

    // region Private Methods
    private void exportCSV() { assert null != this.handler; this.handler.exportCSV(); }
    private void exportSQL() { assert null != this.handler; this.handler.exportSQL(); }
    // endregion

    ExportAlertDialog(final android.content.Context context,
    final org.wheatgenetics.inventory.ExportAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    void show()
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
                this.builder = new android.app.AlertDialog.Builder(this.context)
                    .setTitle         (org.wheatgenetics.inventory.R.string.exportAlertDialogTitle)
                    .setMessage       (org.wheatgenetics.inventory.R.string.exportAlertDialogMsg  )
                    .setPositiveButton(
                        org.wheatgenetics.inventory.R.string.exportAlertDialogPositive,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            { org.wheatgenetics.inventory.ExportAlertDialog.this.exportCSV(); }
                        })
                    .setNegativeButton(
                        org.wheatgenetics.inventory.R.string.exportAlertDialogNegative,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            { org.wheatgenetics.inventory.ExportAlertDialog.this.exportSQL(); }
                        })
                    .setNeutralButton(org.wheatgenetics.inventory.R.string.exportAlertDialogNeutral,
                        org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        this.alertDialog.show();
    }
}