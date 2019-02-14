package org.wheatgenetics.inventory.dataentry;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.util.Log
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.EditText
 * android.widget.TextView
 *
 * org.wheatgenetics.androidlibrary.DebouncingEditorActionListener
 * org.wheatgenetics.androidlibrary.DebouncingEditorActionListener.Receiver
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.inventory.BuildConfig
 * org.wheatgenetics.inventory.R
 *
 * org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog
 * org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler
 */
public class DataEntryFragment extends android.support.v4.app.Fragment
implements org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    {
        public abstract void             setBox(java.lang.String box);
        public abstract java.lang.String getBox()                    ;

        public abstract void addRecord(java.lang.String envid, java.lang.String wt);
    }

    // region Fields
    private org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler handler;

    private android.widget.TextView boxValueTextView         ;
    private android.widget.EditText envidEditText, wtEditText;

    private org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog
        setBoxAlertDialog = null;                                                           // lazy
    // endregion                                                                            //  load

    // region Private Methods
    private static void sendDebugLogMsg(final java.lang.String msg)
    {
        if (org.wheatgenetics.inventory.BuildConfig.DEBUG)
            android.util.Log.d("DataEntryFragment", msg);
    }

    private void addRecord(final java.lang.String envid, final java.lang.String wt)
    { assert null != this.handler; this.handler.addRecord(envid, wt); }

    private void addEnvid(final java.lang.String envid)
    {
        this.addRecord(envid, org.wheatgenetics.androidlibrary.Utils.getText(this.wtEditText));
        this.clearWt();
    }

    private void addWt(final java.lang.String wt)
    {
        this.addRecord(org.wheatgenetics.androidlibrary.Utils.getText(this.envidEditText), wt);
        this.setEnvId("");
    }
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override public void onAttach(final android.content.Context context)
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

    @java.lang.Override public android.view.View onCreateView(
    @android.support.annotation.NonNull final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg("onCreateView()");

        // Inflate the layout for this fragment:
        return inflater.inflate(org.wheatgenetics.inventory.R.layout.fragment_data_entry,
            container,false);
    }

    @java.lang.Override public void onActivityCreated(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg(
            "onActivityCreated()");

        {
            final android.app.Activity activity = this.getActivity();
            assert null != activity; this.boxValueTextView = activity.findViewById(
                org.wheatgenetics.inventory.R.id.boxValueTextView);
            assert null != this.handler; assert null != this.boxValueTextView;
            this.boxValueTextView.setText(this.handler.getBox());

            this.envidEditText =
                activity.findViewById(org.wheatgenetics.inventory.R.id.envidEditText);
            assert null != this.envidEditText; this.envidEditText.setOnEditorActionListener(
                new org.wheatgenetics.androidlibrary.DebouncingEditorActionListener(
                    /* editText => */ this.envidEditText,
                    /* receiver => */ new
                        org.wheatgenetics.androidlibrary.DebouncingEditorActionListener.Receiver()
                        {
                            @java.lang.Override public void receiveText(final java.lang.String text)
                            {
                                org.wheatgenetics.inventory.dataentry
                                    .DataEntryFragment.this.addEnvid(text);
                            }
                        },
                    /* debug       => */ org.wheatgenetics.inventory.BuildConfig.DEBUG,
                    /* delayMillis => */1000));
            this.focusEnvIdEditText();

            this.wtEditText = activity.findViewById(org.wheatgenetics.inventory.R.id.wtEditText);
        }
        assert null != this.wtEditText; this.wtEditText.setOnEditorActionListener(
            new org.wheatgenetics.androidlibrary.DebouncingEditorActionListener(
                /* editText => */ this.wtEditText,
                /* receiver => */ new
                    org.wheatgenetics.androidlibrary.DebouncingEditorActionListener.Receiver()
                    {
                        @java.lang.Override public void receiveText(final java.lang.String text)
                        {
                            org.wheatgenetics.inventory.dataentry
                                .DataEntryFragment.this.addWt(text);
                        }
                    },
                /* debug       => */ org.wheatgenetics.inventory.BuildConfig.DEBUG,
                /* delayMillis => */1000));
    }

    @java.lang.Override public void onDetach()
    {
        org.wheatgenetics.inventory.dataentry.DataEntryFragment.sendDebugLogMsg("onDetach()");
        this.handler = null; super.onDetach();
    }

    // region org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler Overridden Method
    @java.lang.Override public void setBox(final java.lang.String box)
    {
        assert null != this.boxValueTextView; this.boxValueTextView.setText(box);
        assert null != this.handler         ; this.handler.setBox          (box);
    }
    // endregion
    // endregion

    // region Public Methods
    public void handleSetBoxButtonClick(final java.lang.String box)
    {
        if (null == this.setBoxAlertDialog)
            this.setBoxAlertDialog = new org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog(
                this.getActivity(),this);
        this.setBoxAlertDialog.show(box);
    }

    public void clearBox() { this.setBox(""); }

    public void setEnvId(final java.lang.String envId)
    { assert null != this.envidEditText; this.envidEditText.setText(envId); }

    public void setWt(final java.lang.String wt)
    { assert null != this.wtEditText; this.wtEditText.setText(wt); }

    public void clearWt() { this.setWt(""); }

    public void focusEnvIdEditText()
    {
        assert null != this.envidEditText;
        this.envidEditText.requestFocus(); this.envidEditText.selectAll();
    }
    // endregion
}