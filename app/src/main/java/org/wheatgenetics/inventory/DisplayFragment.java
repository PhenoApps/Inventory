package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Context
 * android.os.Bundle
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.ViewGroup
 *
 * org.wheatgenetics.inventory.R
 */
public class DisplayFragment extends android.support.v4.app.Fragment
{
    public interface Handler { public abstract void onFragmentInteraction(java.lang.String s); }

    private static final java.lang.String ARG_PARAM1 = "param1";

    private org.wheatgenetics.inventory.DisplayFragment.Handler handler;
    private java.lang.String                                    param1 ;

    public DisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.inventory.DisplayFragment.Handler)
            this.handler = (org.wheatgenetics.inventory.DisplayFragment.Handler) context;
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
        if (null != arguments) this.param1 =
            arguments.getString(org.wheatgenetics.inventory.DisplayFragment.ARG_PARAM1);
    }

    @java.lang.Override
    public android.view.View onCreateView(final android.view.LayoutInflater inflater,
    final android.view.ViewGroup container, final android.os.Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.inventory.R.layout.fragment_display, container, false);
    }

    @java.lang.Override
    public void onDetach() { this.handler = null; super.onDetach(); }
    // endregion

    // region Public Methods
    public void onButtonPressed(final java.lang.String s)
    { if (this.handler != null) this.handler.onFragmentInteraction(s); }

    public static DisplayFragment newInstance(final java.lang.String param1)
    {
        final org.wheatgenetics.inventory.DisplayFragment result =
            new org.wheatgenetics.inventory.DisplayFragment();
        {
            final android.os.Bundle arguments = new android.os.Bundle();
            arguments.putString(org.wheatgenetics.inventory.DisplayFragment.ARG_PARAM1, param1);
            result.setArguments(arguments);
        }
        return result;
    }
    // endregion
}