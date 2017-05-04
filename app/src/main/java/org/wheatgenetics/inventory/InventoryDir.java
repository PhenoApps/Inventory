package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.net.Uri
 * android.os.Environment
 */

class InventoryDir extends java.lang.Object
{
    // region Private Class Constant
    final private static java.io.File PATH =
        new java.io.File(android.os.Environment.getExternalStorageDirectory(), "Inventory");
    // endregion


    // region Package Class Methods
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
            else throw new java.io.IOException();
    }

    static android.net.Uri parse(final java.lang.String fileName)
    {
        return android.net.Uri.parse(
            org.wheatgenetics.inventory.InventoryDir.PATH.toString() + fileName);
    }

    static java.io.File createNewFile(final java.lang.String fileName) throws java.io.IOException
    {
        final java.io.File file =
            new java.io.File(org.wheatgenetics.inventory.InventoryDir.PATH, fileName);

        file.createNewFile();                                          // throws java.io.IOException
        return file;
    }
    // endregion
}