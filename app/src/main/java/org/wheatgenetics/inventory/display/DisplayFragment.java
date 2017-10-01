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
    private org.wheatgenetics.inventory.display.DisplayFragment.Handler                  handler;
    private android.view.View.OnLongClickListener             onLongClickListenerInstance = null;
    private org.wheatgenetics.inventory.model.InventoryRecord                    inventoryRecord;
    private org.wheatgenetics.inventory.display.DeleteRecordAlertDialog
        deleteRecordAlertDialog = null;
    // endregion

    // region Private Methods
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
    {
        super.onActivityCreated(savedInstanceState);

        assert null != this.handler;
        final org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords =
            this.handler.inventoryRecords();
        if (null != inventoryRecords)
        {
            final android.app.Activity activity = this.getActivity();

            assert null != activity;
            final android.widget.TableLayout tableLayout = (android.widget.TableLayout)
                activity.findViewById(org.wheatgenetics.inventory.R.id.displayTableLayout);

            assert null != tableLayout;
            for (final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord:
            inventoryRecords)
            {
                final org.wheatgenetics.inventory.display.TableRow tableRow =
                    new org.wheatgenetics.inventory.display.TableRow(activity);

                assert null != inventoryRecord;
                tableRow.addView(new org.wheatgenetics.inventory.display.DisplayTextView(
                    activity, inventoryRecord.getPositionAsString()));
                tableRow.addView(new org.wheatgenetics.inventory.display.DisplayTextView(
                    activity, inventoryRecord.getBox()));
                tableRow.addView(new org.wheatgenetics.inventory.display.EnvIdDisplayTextView(
                    activity, inventoryRecord.getEnvId(), inventoryRecord,
                    this.onLongClickListener()));
                tableRow.addView(new org.wheatgenetics.inventory.display.DisplayTextView(
                    activity, inventoryRecord.getWt()));

                tableLayout.addView(tableRow, new android.view.ViewGroup.LayoutParams(
                    android.widget.TableLayout.LayoutParams.MATCH_PARENT,
                    android.widget.TableLayout.LayoutParams.MATCH_PARENT));
            }
        }
    }

    @java.lang.Override
    public void onDetach() { this.handler = null; super.onDetach(); }

    // region org.wheatgenetics.inventory.display.DeleteRecordAlertDialog.HandlerOverridden Method
    @java.lang.Override
    public void deleteRecord()
    { assert null != this.handler; this.handler.deleteRecord(inventoryRecord); }
    // endregion
    // endregion

    public static DisplayFragment newInstance()
    { return new org.wheatgenetics.inventory.display.DisplayFragment(); }
}