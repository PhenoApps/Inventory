package org.wheatgenetics.inventory;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.  Activities that contain this fragment
 * must implement the
 * {@link org.wheatgenetics.inventory.DataEntryFragment.OnFragmentInteractionListener} interface to
 * handle interaction events.  Use the
 * {@link org.wheatgenetics.inventory.DataEntryFragment#newInstance} factory method to create an
 * instance of this fragment.
 *
 * Uses:
 * android.content.Context
 * android.net.Uri
 * android.os.Bundle
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 *
 * org.wheatgenetics.inventory.R
 */
public class DataEntryFragment extends android.support.v4.app.Fragment
{
    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.  <p>See the Android Training lesson <a
     * href="http://developer.android.com/training/basics/fragments/communicating.html">Communicating
     * with Other Fragments</a> for more information.</p>
     */
    interface OnFragmentInteractionListener
    { /* TODO: Update argument type and name. */ void onFragmentInteraction(android.net.Uri uri); }

    // TODO: Rename parameter arguments: choose names that match the fragment initialization parame-
    // TODO: ters, e.g., ARG_ITEM_NUMBER.
    private static final java.lang.String ARG_PARAM1 = "param1", ARG_PARAM2 = "param2";

    // region Fields
    // TODO: Rename and change types of parameters.
    private java.lang.String param1, param2;

    private org.wheatgenetics.inventory.DataEntryFragment.OnFragmentInteractionListener listener;
    // endregion

    public DataEntryFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof
        org.wheatgenetics.inventory.DataEntryFragment.OnFragmentInteractionListener)
            this.listener =
                (org.wheatgenetics.inventory.DataEntryFragment.OnFragmentInteractionListener)
                    context;
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
        if (null != arguments)
        {
            this.param1 = arguments.getString(
                org.wheatgenetics.inventory.DataEntryFragment.ARG_PARAM1);
            this.param2 = arguments.getString(
                org.wheatgenetics.inventory.DataEntryFragment.ARG_PARAM2);
        }
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
    public void onDetach() { super.onDetach(); this.listener = null; }
    // endregion

    // TODO: Rename method, update argument and hook method into UI event.
    public void onButtonPressed(final android.net.Uri uri)
    { if (null != this.listener) this.listener.onFragmentInteraction(uri); }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataEntryFragment.
     */
    // TODO: Rename and change types and number of parameters.
    public static org.wheatgenetics.inventory.DataEntryFragment newInstance(
    final java.lang.String param1, final java.lang.String param2)
    {
        org.wheatgenetics.inventory.DataEntryFragment result =
            new org.wheatgenetics.inventory.DataEntryFragment();
        {
            final android.os.Bundle arguments = new android.os.Bundle();
            arguments.putString(org.wheatgenetics.inventory.DataEntryFragment.ARG_PARAM1, param1);
            arguments.putString(org.wheatgenetics.inventory.DataEntryFragment.ARG_PARAM2, param2);
            result.setArguments(arguments);
        }
        return result;
    }
}