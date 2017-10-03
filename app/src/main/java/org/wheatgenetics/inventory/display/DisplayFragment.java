package org.wheatgenetics.inventory.display;

/**
 * Uses:
 * android.app.Activity
 * android.content.Context
 * android.os.Bundle
 * android.support.annotation.Nullable
 * android.support.v4.app.Fragment
 * android.view.LayoutInflater
 * android.view.View
 * android.view.View.OnLongClickListener
 * android.view.ViewGroup
 * android.view.ViewGroup.LayoutParams
 * android.widget.ScrollView
 * android.widget.TableLayout
 * android.widget.TableLayout.LayoutParams
 *
 * org.wheatgenetics.inventory.R
 *
 * org.wheatgenetics.inventory.model.InventoryRecord
 * org.wheatgenetics.inventory.model.InventoryRecords
 *
 * org.wheatgenetics.inventory.display.DeleteRecordAlertDialog
 * org.wheatgenetics.inventory.display.DeleteRecordAlertDialog.Handler
 */
public class DisplayFragment extends android.support.v4.app.Fragment
implements org.wheatgenetics.inventory.display.DeleteRecordAlertDialog.Handler
{
    public interface Handler
    {
        public abstract org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords();
        public abstract boolean deleteRecord(
            org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord);
    }

    // region Fields
    private org.wheatgenetics.inventory.display.DisplayFragment.Handler handler                ;
    private boolean                                                     shouldGoToBottom = true;

    private org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord;
    private org.wheatgenetics.inventory.display.DeleteRecordAlertDialog
        deleteRecordAlertDialog = null;
    private android.view.View.OnLongClickListener onLongClickListenerInstance = null;

    private android.widget.ScrollView           scrollViewInstance   = null;
    private java.lang.Runnable                  scrollActionInstance = null;
    private android.view.ViewGroup.LayoutParams layoutParamsInstance = null;
    private android.widget.TableLayout          tableLayoutInstance  = null;

    /**
     * Position of last inventoryRecord added.  If == 0 then no inventoryRecord has been added yet.
     * This field should never be negative.
     */
    private int position;
    // endregion

    // region Private Methods
    private android.widget.ScrollView scrollView()
    {
        if (null == this.scrollViewInstance)
        {
            this.scrollViewInstance = (android.widget.ScrollView)
                this.getActivity().findViewById(org.wheatgenetics.inventory.R.id.displayScrollView);
            assert null != this.scrollViewInstance;
        }
        return this.scrollViewInstance;
    }

    private void scrollDown()
    { this.scrollView().fullScroll(android.widget.ScrollView.FOCUS_DOWN); }

    private java.lang.Runnable scrollAction()
    {
        if (null == this.scrollActionInstance) this.scrollActionInstance = new java.lang.Runnable()
            {
                @java.lang.Override
                public void run()
                { org.wheatgenetics.inventory.display.DisplayFragment.this.scrollDown(); }
            };
        return this.scrollActionInstance;
    }

    private void goToBottom(final boolean shortDelay)
    {
        this.scrollView().postDelayed(this.scrollAction(),
            /* delayMillis => */ shortDelay ? 500 : 1500);
    }

    private android.view.ViewGroup.LayoutParams layoutParams()
    {
        if (null == this.layoutParamsInstance)
            this.layoutParamsInstance = new android.view.ViewGroup.LayoutParams(
                android.widget.TableLayout.LayoutParams.MATCH_PARENT,
                android.widget.TableLayout.LayoutParams.MATCH_PARENT);
        return this.layoutParamsInstance;
    }

    private android.widget.TableLayout tableLayout()
    {
        if (null == this.tableLayoutInstance)
        {
            this.tableLayoutInstance =
                (android.widget.TableLayout) this.getActivity().findViewById(
                    org.wheatgenetics.inventory.R.id.displayTableLayout);
            assert null != this.tableLayoutInstance;
        }
        return this.tableLayoutInstance;
    }

    private void deleteRecord(final java.lang.Object tag)
    {
        if (null != tag) if (tag instanceof org.wheatgenetics.inventory.model.InventoryRecord)
        {
            if (null == this.deleteRecordAlertDialog) this.deleteRecordAlertDialog =
                new org.wheatgenetics.inventory.display.DeleteRecordAlertDialog(
                    this.getActivity(), this);
            this.inventoryRecord = (org.wheatgenetics.inventory.model.InventoryRecord) tag;
            this.deleteRecordAlertDialog.show(this.inventoryRecord.getEnvId());
        }
    }

    private android.view.View.OnLongClickListener onLongClickListener()
    {
        if (null == this.onLongClickListenerInstance) this.onLongClickListenerInstance =
            new android.view.View.OnLongClickListener()
            {
                @java.lang.Override
                public boolean onLongClick(final android.view.View v)
                {
                    assert null != v;
                    org.wheatgenetics.inventory.display.DisplayFragment.this.deleteRecord(
                        v.getTag());
                    return true;
                }
            };
        return this.onLongClickListenerInstance;
    }

    private void addTableRows(final boolean shortDelay)
    {
        assert null != this.handler;
        final org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords =
            this.handler.inventoryRecords();
        if (null == inventoryRecords)
            this.position = 0;
        else
            if (inventoryRecords.isEmpty())
                this.position = 0;
            else
            {
                this.shouldGoToBottom = false;
                try
                {
                    for (final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord:
                    inventoryRecords)
                        this.addTableRow(inventoryRecord);
                }
                finally { this.shouldGoToBottom = true; }
                this.goToBottom(shortDelay);
            }
    }
    // endregion

    public DisplayFragment() { /* Required empty public constructor. */ }

    // region Overridden Methods
    @java.lang.Override
    public void onAttach(final android.content.Context context)
    {
        super.onAttach(context);

        if (context instanceof org.wheatgenetics.inventory.display.DisplayFragment.Handler)
            this.handler = (org.wheatgenetics.inventory.display.DisplayFragment.Handler) context;
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
        // Inflate the layout for this fragment:
        assert null != inflater; return inflater.inflate(
            org.wheatgenetics.inventory.R.layout.fragment_display, container, false);
    }

    @java.lang.Override
    public void onActivityCreated(
    @android.support.annotation.Nullable final android.os.Bundle savedInstanceState)
    { super.onActivityCreated(savedInstanceState); this.addTableRows(/* shortDelay => */ false); }

    @java.lang.Override
    public void onDetach() { this.handler = null; super.onDetach(); }

    // region org.wheatgenetics.inventory.display.DeleteRecordAlertDialog.HandlerOverridden Method
    @java.lang.Override
    public void deleteRecord()
    { assert null != this.handler; this.handler.deleteRecord(inventoryRecord); }
    // endregion
    // endregion

    // region Public Methods
    public int getPosition() { return this.position; }

    public void addTableRow(final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    {
        if (null != inventoryRecord)
        {
            {
                final android.app.Activity                         activity = this.getActivity();
                final org.wheatgenetics.inventory.display.TableRow tableRow =
                    new org.wheatgenetics.inventory.display.TableRow(activity);

                tableRow.addView(new org.wheatgenetics.inventory.display.DisplayTextView(
                    activity, inventoryRecord.getPositionAsString()));
                tableRow.addView(new org.wheatgenetics.inventory.display.DisplayTextView(
                    activity, inventoryRecord.getBox()));
                tableRow.addView(new org.wheatgenetics.inventory.display.EnvIdDisplayTextView(
                    activity, inventoryRecord.getEnvId(), inventoryRecord,
                    this.onLongClickListener()));
                tableRow.addView(new org.wheatgenetics.inventory.display.DisplayTextView(
                    activity, inventoryRecord.getWt()));

                this.tableLayout().addView(tableRow, this.layoutParams());
            }
            this.position = inventoryRecord.getPosition();
            if (this.shouldGoToBottom) this.goToBottom(/* shortDelay => */ true);
        }
    }

    public void refresh()
    { this.tableLayout().removeAllViews(); this.addTableRows(/* shortDelay => */ true); }
    // endregion
}