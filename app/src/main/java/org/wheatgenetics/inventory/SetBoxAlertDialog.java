package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.R
 */
class SetBoxAlertDialog extends java.lang.Object
{
    interface Handler
    { public abstract void setBox(java.lang.String box); public abstract void cancel(); }

    // region Fields
    private final android.content.Context                               context;
    private final org.wheatgenetics.inventory.SetBoxAlertDialog.Handler handler;

    private android.app.AlertDialog         alertDialog = null;
    private android.app.AlertDialog.Builder builder     = null;
    private android.widget.EditText         editText    = null;
    // endregion

    SetBoxAlertDialog(final android.content.Context context,
    final org.wheatgenetics.inventory.SetBoxAlertDialog.Handler handler)
    { super(); this.context = context; this.handler = handler; }

    // region Private Methods
    private void setBox()
    {
        assert null != this.editText; assert null != this.handler;
        this.handler.setBox(org.wheatgenetics.androidlibrary.Utils.getText(this.editText));
    }

    private void cancel() { assert null != this.handler; this.handler.cancel(); }
    // endregion

    void show(final java.lang.String box)
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                if (null == this.editText)
                    (this.editText = new android.widget.EditText(this.context)).setSingleLine();
                this.builder = new android.app.AlertDialog.Builder(this.context)
                    .setTitle(org.wheatgenetics.inventory.R.string.setBoxAlertDialogTitle)
                    .setView (this.editText                                              )
                    .setPositiveButton(org.wheatgenetics.androidlibrary.R.string.okButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            { org.wheatgenetics.inventory.SetBoxAlertDialog.this.setBox(); }
                        })
                    .setNegativeButton(org.wheatgenetics.androidlibrary.R.string.cancelButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                assert dialog != null; dialog.cancel();
                                org.wheatgenetics.inventory.SetBoxAlertDialog.this.cancel();
                            }
                        });
            }
            this.alertDialog = this.builder.create();
            assert null != this.alertDialog;
        }
        assert null != this.editText; this.editText.setText(box); this.editText.selectAll();
        this.alertDialog.show();
    }
}