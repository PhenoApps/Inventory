package org.wheatgenetics.inventory.navigation;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.R
 */
class ExportAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void exportCSV(); public abstract void exportSQL(); }

    private final org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler handler;

    // region Private Methods
    private void exportCSV() { assert null != this.handler; this.handler.exportCSV(); }
    private void exportSQL() { assert null != this.handler; this.handler.exportSQL(); }
    // endregion

    ExportAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.inventory.navigation.ExportAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle         (org.wheatgenetics.inventory.R.string.exportAlertDialogTitle)
            .setMessage       (org.wheatgenetics.inventory.R.string.exportAlertDialogMsg  )
            .setPositiveButton(org.wheatgenetics.inventory.R.string.exportAlertDialogPositive,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    { org.wheatgenetics.inventory.navigation.ExportAlertDialog.this.exportCSV(); }
                })
            .setNegativeButton(org.wheatgenetics.inventory.R.string.exportAlertDialogNegative,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    { org.wheatgenetics.inventory.navigation.ExportAlertDialog.this.exportSQL(); }
                })
            .setNeutralButton(org.wheatgenetics.inventory.R.string.exportAlertDialogNeutral,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }
}