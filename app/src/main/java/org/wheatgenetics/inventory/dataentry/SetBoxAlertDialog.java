package org.wheatgenetics.inventory.dataentry;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.view.inputmethod.InputMethodManager
 * android.widget.EditText
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.R
 */
class SetBoxAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    interface Handler { public abstract void setBox(java.lang.String box); }

    // region Fields
    private final org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler handler;

    private android.widget.EditText                     editText                         ;
    private android.view.inputmethod.InputMethodManager inputMethodManagerInstance = null;
    // endregion

    // region Private Methods
    private android.view.inputmethod.InputMethodManager inputMethodManager()
    {
        if (null == this.inputMethodManagerInstance)
            this.inputMethodManagerInstance = (android.view.inputmethod.InputMethodManager)
                this.activity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
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

    SetBoxAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override
    public void configure()
    {
        (this.editText = new android.widget.EditText(this.activity())).setSingleLine();
        this.setTitle(org.wheatgenetics.inventory.R.string.setBoxAlertDialogTitle)
            .setView(this.editText)
            .setOKPositiveButton(new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    { org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.this.setBox(); }
                })
            .setCancelNegativeButton(new android.content.DialogInterface.OnClickListener()
                {
                    @java.lang.Override
                    public void onClick(final android.content.DialogInterface dialog,
                    final int which)
                    {
                        org.wheatgenetics.inventory.dataentry.
                            SetBoxAlertDialog.this.hideSoftInputFromWindow();
                    }
                });
    }

    void show(final java.lang.String box)
    {
        assert null != this.editText; this.editText.setText(box); this.editText.selectAll();
        this.show();
    }
}