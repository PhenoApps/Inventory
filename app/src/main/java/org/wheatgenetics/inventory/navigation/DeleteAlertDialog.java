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
class DeleteAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler { public abstract void delete(); }

    private final org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler handler;

    private void delete() { assert null != this.handler; this.handler.delete(); }

    DeleteAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.inventory.navigation.DeleteAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        this.setTitle(org.wheatgenetics.inventory.R.string.deleteAlertDialogTitle)
            .setCancelableToFalse()
            .setMessage(org.wheatgenetics.inventory.R.string.deleteAlertDialogMsg)
            .setPositiveButton(org.wheatgenetics.inventory.R.string.deleteAlertDialogPositive,
                new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    { org.wheatgenetics.inventory.navigation.DeleteAlertDialog.this.delete(); }
                })
            .setNegativeButton(org.wheatgenetics.inventory.R.string.deleteAlertDialogNegative,
                org.wheatgenetics.androidlibrary.Utils.cancellingOnClickListener());
    }
}