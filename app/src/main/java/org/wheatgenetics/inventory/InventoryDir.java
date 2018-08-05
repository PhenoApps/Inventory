package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.app.Activity
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 *
 * org.wheatgenetics.androidlibrary.RequestDir
 * org.wheatgenetics.androidlibrary.Utils
 */
class InventoryDir extends org.wheatgenetics.androidlibrary.RequestDir
{
    InventoryDir(final android.app.Activity activity, final int requestCode)
    { super(activity,"Inventory",".inventory", requestCode); }

    @java.lang.Override public java.io.File createNewFile(final java.lang.String fileName)
    throws java.io.IOException, org.wheatgenetics.javalib.Dir.PermissionException
    {
        return org.wheatgenetics.androidlibrary.Utils.makeFileDiscoverable(this.getActivity(),
            super.createNewFile(fileName) /* throws java.io.IOException,
                org.wheatgenetics.javalib.Dir.PermissionException */);
    }
}