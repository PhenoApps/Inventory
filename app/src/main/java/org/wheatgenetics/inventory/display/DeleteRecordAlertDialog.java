package org.wheatgenetics.inventory.display;

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
class DeleteRecordAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    { public abstract void deleteRecord(); }

    private final org.wheatgenetics.inventory.display.DeleteRecordAlertDialog.Handler handler;

    private void deleteRecord() { assert null != this.handler; this.handler.deleteRecord(); }

    DeleteRecordAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.inventory.display.DeleteRecordAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    {
        this.setTitle(org.wheatgenetics.inventory.R.string.deleteRecordAlertDialogTitle)
            .setCancelableToTrue()
            .setPositiveButton(org.wheatgenetics.inventory.R.string.deleteRecordAlertDialogPositive,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override public void onClick(
                    final android.content.DialogInterface dialog, final int which)
                    {
                        org.wheatgenetics.inventory.display.
                            DeleteRecordAlertDialog.this.deleteRecord();
                    }
                })
            .setNegativeButton(org.wheatgenetics.inventory.R.string.deleteRecordAlertDialogNegative,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }

    void show(final java.lang.String envId)
    { this.setMessage("Delete " + envId + "?").createShow(); }
}