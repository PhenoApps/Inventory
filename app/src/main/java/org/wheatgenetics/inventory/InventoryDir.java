package org.wheatgenetics.inventory;

// Uses android.os.Environment and android.net.Uri.

class InventoryDir
{
    static java.io.File PATH =
        new java.io.File(android.os.Environment.getExternalStorageDirectory(), "Inventory");

    /**
     * The following code was originally in MainActivity.createDir().  This name gives a clue as to
     * the purpose of the code, although it doesn't explain why blankHiddenFile is created.
     */
    static java.io.File createIfMissing() throws java.io.IOException
    {
        if (org.wheatgenetics.inventory.InventoryDir.PATH.exists())
            return null;
        else
            if (org.wheatgenetics.inventory.InventoryDir.PATH.mkdirs())
            {
                final java.io.File blankHiddenFile = new java.io.File(
                    org.wheatgenetics.inventory.InventoryDir.PATH, ".inventory");

                if (blankHiddenFile.getParentFile().mkdirs())          // Wasn't this already done?
                    blankHiddenFile.createNewFile();                   // throws java.io.IOException
                else
                    throw new java.io.IOException();
                return blankHiddenFile;
            }
            else
                throw new java.io.IOException();
    }

    static android.net.Uri parse(final java.lang.String filePath)
    {
        return android.net.Uri.parse(
            org.wheatgenetics.inventory.InventoryDir.PATH.toString() + filePath);
    }
}