package org.wheatgenetics.inventory.display;

/**
 * Uses:
 * android.content.Context
 * android.widget.TableLayout.LayoutParams
 * android.widget.TableRow
 */
class TableRow extends android.widget.TableRow
{
    TableRow(final android.content.Context context)
    {
        super(context);
        this.setLayoutParams(new android.widget.TableLayout.LayoutParams(
            android.widget.TableLayout.LayoutParams.WRAP_CONTENT,
            android.widget.TableLayout.LayoutParams.WRAP_CONTENT));
    }
}