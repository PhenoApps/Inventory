package org.wheatgenetics.inventory.dataentry;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.net.Uri
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.util.Log
 * android.view.KeyEvent
 * android.view.LayoutInflater
 * android.view.View
 * android.view.View.OnClickListener
 * android.view.ViewGroup
 * android.view.inputmethod.EditorInfo
 * android.widget.Button
 * android.widget.EditText
 * android.widget.TextView
 * android.widget.TextView.OnEditorActionListener
 *
 * org.wheatgenetics.inventory.BuildConfig
 * org.wheatgenetics.inventory.R
 *
 * org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog
 * org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler
 */
public class DataEntryFragment extends android.support.v4.app.Fragment implements
android.view.View.OnClickListener,                                  // for setBoxButton
org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler,
android.widget.TextView.OnEditorActionListener                      // for envidEditText, wtEditText
{
    public interface Handler
    { /* TODO: Update argument type and name. */ void onFragmentInteraction(android.net.Uri uri); }

    private static final java.lang.String BOX = "box";

    // region Fields
    private org.wheatgenetics.inventory.dataentry.DataEntryFragment.Handler handler;
    private java.lang.String        box                      ;
    private android.widget.TextView boxValueTextView         ;
    private android.widget.EditText envidEditText, wtEditText;

    private org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog setBoxAlertDialog = null;
    // endregion

    // region Private Methods
    private void setBoxValueTextViewText()
    { assert null != this.boxValueTextView; this.boxValueTextView.setText(this.box); }

    private void setBoxValueTextViewText(final java.lang.String box)
    { this.box = box; this.setBoxValueTextViewText(); }

    private void setBox()
    {
        if (null == this.setBoxAlertDialog) this.setBoxAlertDialog =
            new org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog(this.getActivity(), this);
        this.setBoxAlertDialog.show(this.box);
    }
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

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
    public void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final android.os.Bundle arguments = this.getArguments();
        if (null != arguments) this.box =
            arguments.getString(org.wheatgenetics.inventory.dataentry.DataEntryFragment.BOX);
    }

    @java.lang.Override
    public android.view.View onCreateView(final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.inventory.R.layout.fragment_data_entry, container, false);
    }

    @java.lang.Override
    public void onActivityCreated(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final android.app.Activity activity = this.getActivity();
        assert null != activity; this.boxValueTextView = (android.widget.TextView)
            activity.findViewById(org.wheatgenetics.inventory.R.id.boxValueTextView);
        this.setBoxValueTextViewText();

        {
            final android.widget.Button button = (android.widget.Button)
                activity.findViewById(org.wheatgenetics.inventory.R.id.setBoxButton);
            assert null != button; button.setOnClickListener(this);
        }

        this.envidEditText = (android.widget.EditText)
            activity.findViewById(org.wheatgenetics.inventory.R.id.envidEditText);
        assert null != this.envidEditText; this.envidEditText.setOnEditorActionListener(this);

        this.wtEditText = (android.widget.EditText)
            activity.findViewById(org.wheatgenetics.inventory.R.id.wtEditText);
        assert null != this.wtEditText; this.wtEditText.setOnEditorActionListener(this);
    }

    @java.lang.Override
    public void onDetach() { super.onDetach(); this.handler = null; }

    // region android.view.View.OnClickListener Overridden Method
    @java.lang.Override
    public void onClick(final android.view.View v) { this.setBox(); }
    // endregion

    // region org.wheatgenetics.inventory.dataentry.SetBoxAlertDialog.Handler Overridden Method
    @java.lang.Override
    public void setBox(final java.lang.String box) { this.setBoxValueTextViewText(box);}
    // endregion

    // region android.widget.TextView.OnEditorActionListener Overridden Method
    @java.lang.Override
    public boolean onEditorAction(final android.widget.TextView v, final int actionId,
    final android.view.KeyEvent event)
    {
        if (org.wheatgenetics.inventory.BuildConfig.DEBUG)
        {
            final java.lang.StringBuffer msg = new java.lang.StringBuffer("actionId == ");
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
            android.util.Log.d("DataEntryFragment", msg.toString());
        }
        return false;
    }
    // endregion
    // endregion

    // TODO: Rename method, update argument and hook method into UI event.
    public void onButtonPressed(final android.net.Uri uri)
    { if (null != this.handler) this.handler.onFragmentInteraction(uri); }

    public static org.wheatgenetics.inventory.dataentry.DataEntryFragment newInstance(
    final java.lang.String box)
    {
        final org.wheatgenetics.inventory.dataentry.DataEntryFragment result =
            new org.wheatgenetics.inventory.dataentry.DataEntryFragment();
        {
            final android.os.Bundle arguments = new android.os.Bundle();
            arguments.putString(org.wheatgenetics.inventory.dataentry.DataEntryFragment.BOX, box);
            result.setArguments(arguments);
        }
        return result;
    }
}