package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Context
 * android.net.Uri
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.widget.TextView
 *
 * org.wheatgenetics.inventory.R
 */
public class DataEntryFragment extends android.support.v4.app.Fragment
{
    interface Handler
    { /* TODO: Update argument type and name. */ void onFragmentInteraction(android.net.Uri uri); }

    private static final java.lang.String BOX = "box";

    // region Fields
    private org.wheatgenetics.inventory.DataEntryFragment.Handler handler         ;
    private java.lang.String                                      box             ;
    private android.widget.TextView                               boxValueTextView;
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.inventory.DataEntryFragment.Handler)
            this.handler = (org.wheatgenetics.inventory.DataEntryFragment.Handler) context;
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
        if (null != arguments)
            this.box = arguments.getString(org.wheatgenetics.inventory.DataEntryFragment.BOX);
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
        this.boxValueTextView = (android.widget.TextView)
            this.getActivity().findViewById(org.wheatgenetics.inventory.R.id.boxValueTextView);
        assert null != this.boxValueTextView; this.boxValueTextView.setText(this.box);
    }

    @java.lang.Override
    public void onDetach() { super.onDetach(); this.handler = null; }
    // endregion

    // TODO: Rename method, update argument and hook method into UI event.
    public void onButtonPressed(final android.net.Uri uri)
    { if (null != this.handler) this.handler.onFragmentInteraction(uri); }

    static org.wheatgenetics.inventory.DataEntryFragment newInstance(final java.lang.String box)
    {
        org.wheatgenetics.inventory.DataEntryFragment result =
            new org.wheatgenetics.inventory.DataEntryFragment();
        {
            final android.os.Bundle arguments = new android.os.Bundle();
            arguments.putString(org.wheatgenetics.inventory.DataEntryFragment.BOX, box);
            result.setArguments(arguments);
        }
        return result;
    }
}