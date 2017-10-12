package org.wheatgenetics.inventory.dataentry;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.util.Log
 * android.view.KeyEvent
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.view.inputmethod.EditorInfo
 * android.widget.EditText
 * android.widget.TextView
 * android.widget.TextView.OnEditorActionListener
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.BuildConfig
 * org.wheatgenetics.inventory.R
 *
 * org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog
 * org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler
 */
public class DataEntryFragment extends android.support.v4.app.Fragment implements
org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler,    // for SetBoxAlertDialog
android.widget.TextView.OnEditorActionListener                      // for envidEditText, wtEditText
{
    public interface Handler
    {
        public abstract void             setBox(java.lang.String box);
        public abstract java.lang.String getBox()                    ;

        public abstract void addRecord(java.lang.String envid, java.lang.String wt);
    }

    // region Fields
    private org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler handler;

    private android.widget.TextView boxValueTextView         ;
    private android.widget.EditText envidEditText, wtEditText;

    private org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog setBoxAlertDialog = null;
    // endregion

    // region Private Methods
    private static int sendDebugLogMsg(final java.lang.String tag, final java.lang.String msg)
    { return org.wheatgenetics.inventory.BuildConfig.DEBUG ? android.util.Log.d(tag, msg) : 0; }

    private static int sendDebugLogMsg(final java.lang.String msg)
    {
        return org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg(
            "DataEntryFragmentLifecycle", msg);
    }

    private boolean focusEnvIdEditText()
    {
        assert null != this.envidEditText;
        this.envidEditText.requestFocus(); this.envidEditText.selectAll();
        return true;
    }
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg("onAttach()");

        if (context instanceof org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler)
            this.handler =
                (org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler) context;
        else
        {
            assert null != context; throw new java.lang.RuntimeException(context.toString() +
                " must implement Handler");
        }
    }

    @java.lang.Override
    public android.view.View onCreateView(final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg("onCreateView()");

        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.inventory.R.layout.fragment_data_entry, container, false);
    }

    @java.lang.Override
    public void onActivityCreated(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg(
            "onActivityCreated()");

        final android.app.Activity activity = this.getActivity();
        assert null != activity; this.boxValueTextView = (android.widget.TextView)
            activity.findViewById(org.wheatgenetics.inventory.R.id.boxValueTextView);
        assert null != this.handler; assert null != this.boxValueTextView;
        this.boxValueTextView.setText(this.handler.getBox());

        this.envidEditText = (android.widget.EditText)
            activity.findViewById(org.wheatgenetics.inventory.R.id.envidEditText);
        assert null != this.envidEditText; this.envidEditText.setOnEditorActionListener(this);
        this.focusEnvIdEditText();

        this.wtEditText = (android.widget.EditText)
            activity.findViewById(org.wheatgenetics.inventory.R.id.wtEditText);
        assert null != this.wtEditText; this.wtEditText.setOnEditorActionListener(this);
    }

    @java.lang.Override
    public void onDetach()
    {
        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg("onDetach()");
        this.handler = null; super.onDetach();
    }

    // region org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void setBox(final java.lang.String box)
    {
        assert null != this.boxValueTextView; this.boxValueTextView.setText(box);
        assert null != this.handler; this.handler.setBox(box);
    }
    // endregion

    // region android.widget.TextView.OnEditorActionListener Overridden Method
    @java.lang.Override
    public boolean onEditorAction(final android.widget.TextView v, final int actionId,
    final android.view.KeyEvent event)
    {
        if (org.wheatgenetics.inventory.BuildConfig.DEBUG)
        {
            final java.lang.StringBuilder msg = new java.lang.StringBuilder("actionId == ");
            switch (actionId)
            {
                case android.view.inputmethod.EditorInfo.IME_ACTION_NEXT:
                    msg.append("IME_ACTION_NEXT"); break;

                case android.view.inputmethod.EditorInfo.IME_ACTION_DONE:
                    msg.append("IME_ACTION_DONE"); break;

                case android.view.inputmethod.EditorInfo.IME_NULL: msg.append("IME_NULL"); break;
                default                                          : msg.append(actionId  ); break;
            }
            msg.append(", event == "); if (null != event) msg.append("not "); msg.append("null");
            org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg(
                "DataEntryFragmentActions", msg.toString());
        }

        switch (actionId)
        {
            case android.view.inputmethod.EditorInfo.IME_NULL:
                assert null != event;
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) return true;

            case android.view.inputmethod.EditorInfo.IME_ACTION_DONE:
                final java.lang.String envid =
                    org.wheatgenetics.androidlibrary.Utils.getText(this.envidEditText);
                if (null != envid) if (envid.length() > 0)
                {
                    assert null != this.handler; this.handler.addRecord(envid,
                        org.wheatgenetics.androidlibrary.Utils.getText(this.wtEditText));
                    assert null != this.envidEditText; this.envidEditText.setText("");
                }
                return this.focusEnvIdEditText();

            default: return false;
        }
    }
    // endregion
    // endregion

    // region Public Methods
    public void handleSetBoxButtonClick(final java.lang.String box)
    {
        if (null == this.setBoxAlertDialog) this.setBoxAlertDialog =
            new org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog(this.getActivity(), this);
        this.setBoxAlertDialog.show(box);
    }

    public void clearBox() { this.setBox(""); }

    public void setEnvId(final java.lang.String envId)
    { assert null != this.envidEditText; this.envidEditText.setText(envId); }

    public void setWt(final java.lang.String wt)
    { assert null != this.wtEditText; this.wtEditText.setText(wt); }

    public void clearWt() { this.setWt(""); }
    // endregion
}