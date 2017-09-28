package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.Context
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.androidlibrary.Dir
 * org.wheatgenetics.androidlibrary.Utils
 */
class InventoryDir extends org.wheatgenetics.androidlibrary.Dir
{
    InventoryDir(final android.content.Context context)
    { super(context, "Inventory", ".inventory"); }

    @java.lang.Override
    public java.io.File createNewFile(final java.lang.String fileExt)
    {
        return org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(this.getContext(),
            super.createNewFile("inventory_" +
                org.wheatgenetics.javalib.Utils.getDateTime() + '.' + fileExt));
    }
}