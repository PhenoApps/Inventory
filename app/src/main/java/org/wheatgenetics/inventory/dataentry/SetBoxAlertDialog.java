package org.wheatgenetics.inventory.dataentry;

/**
 * Uses:
 * android.app.Activity
 * android.app.AlertDialog
 * android.app.AlertDialog.Builder
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.inputmethod.InputMethodManager
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.R
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.R
 */
class SetBoxAlertDialog extends java.lang.Object
{
    interface Handler { public abstract void setBox(java.lang.String box); }

    // region Fields
    private final android.app.Activity                                            activity;
    private final org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler handler ;

    private android.app.AlertDialog                     alertDialog                = null;
    private android.app.AlertDialog.Builder             builder                    = null;
    private android.widget.EditText                     editText                   = null;
    private android.view.inputmethod.InputMethodManager inputMethodManagerInstance = null;
    // endregion

    SetBoxAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler handler)
    { super(); this.activity = activity; this.handler = handler; }

    // region Private Methods
    private android.view.inputmethod.InputMethodManager inputMethodManager()
    {
        if (null == this.inputMethodManagerInstance)
        {
            assert null != this.activity;
            this.inputMethodManagerInstance = (android.view.inputmethod.InputMethodManager)
                this.activity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        }
        return this.inputMethodManagerInstance;
    }

    private void hideSoftInputFromWindow()
    {
        assert null != this.editText;
        this.inputMethodManager().hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
    }

    private void setBox()
    {
        assert null != this.editText; assert null != this.handler;
        this.handler.setBox(org.wheatgenetics.androidlibrary.Utils.getText(this.editText));
        this.hideSoftInputFromWindow();
    }
    // endregion

    void show(final java.lang.String box)
    {
        if (null == this.alertDialog)
        {
            if (null == this.builder)
            {
                if (null == this.editText)
                    (this.editText = new android.widget.EditText(this.activity)).setSingleLine();
                this.builder = new android.app.AlertDialog.Builder(this.activity)
                    .setTitle(org.wheatgenetics.inventory.R.string.setBoxAlertDialogTitle)
                    .setView (this.editText                                              )
                    .setPositiveButton(org.wheatgenetics.androidlibrary.R.string.okButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                org.wheatgenetics.inventory.dataentry.
                                    SetBoxAlertDialog.this.setBox();
                            }
                        })
                    .setNegativeButton(org.wheatgenetics.androidlibrary.R.string.cancelButtonText,
                        new android.content.DialogInterface.OnClickListener()
                        {
                            @java.lang.Override
                            public void onClick(final android.content.DialogInterface dialog,
                            final int which)
                            {
                                assert dialog != null; dialog.cancel();
                                org.wheatgenetics.inventory.dataentry.
                                    SetBoxAlertDialog.this.hideSoftInputFromWindow();
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