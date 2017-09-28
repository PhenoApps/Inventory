package org.wheatgenetics.inventory.dataentry;

/**
 * Uses:
 * android.content.Context
 * android.net.Uri
 * android.os.Bundle
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 * android.support.v4.app.Fragment
 *
 * org.wheatgenetics.inventory.R
 */
public class DataEntryFragment extends android.support.v4.app.Fragment
{
    public interface OnFragmentInteractionListener
    { /* TODO: Update argument type and name. */ void onFragmentInteraction(android.net.Uri uri); }

    private static final java.lang.String ARG_PARAM1 = "param1";

    // region Fields
    private OnFragmentInteractionListener mListener;
    private java.lang.String              mParam1  ;
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.inventory.dataentry.DataEntryFragment.OnFragmentInteractionListener)
            this.mListener =
                (org.wheatgenetics.inventory.dataentry.DataEntryFragment.OnFragmentInteractionListener) context;
        else
        {
            assert null != context; throw new java.lang.RuntimeException(context.toString() +
                " must implement OnFragmentInteractionListener");
        }
    }

    @java.lang.Override
    public void onCreate(final android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final android.os.Bundle arguments = this.getArguments();
        if (null != arguments) this.mParam1 =
            arguments.getString(org.wheatgenetics.inventory.dataentry.DataEntryFragment.ARG_PARAM1);
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
    public void onDetach() { super.onDetach(); this.mListener = null; }
    // endregion

    // TODO: Rename method, update argument and hook method into UI event.
    public void onButtonPressed(final android.net.Uri uri)
    { if (null != this.mListener) this.mListener.onFragmentInteraction(uri); }

    public static org.wheatgenetics.inventory.dataentry.DataEntryFragment newInstance(
    final java.lang.String param1)
    {
        final org.wheatgenetics.inventory.dataentry.DataEntryFragment result =
            new org.wheatgenetics.inventory.dataentry.DataEntryFragment();
        {
            final android.os.Bundle arguments = new android.os.Bundle();
            arguments.putString(org.wheatgenetics.inventory.dataentry.DataEntryFragment.ARG_PARAM1, param1);
            result.setArguments(arguments);
        }
        return result;
    }
}