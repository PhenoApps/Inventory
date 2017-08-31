package org.wheatgenetics.inventory.model;

/**
 * Uses:
 * android.util.Log
 *
 * org.wheatgenetics.inventory.model.InventoryRecord
 */
class InventoryRecords extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.inventory.model.InventoryRecord>
{
    private final java.util.LinkedList<org.wheatgenetics.inventory.model.InventoryRecord>
        linkedList = new java.util.LinkedList<org.wheatgenetics.inventory.model.InventoryRecord>();

    @java.lang.Override
    public java.util.Iterator<org.wheatgenetics.inventory.model.InventoryRecord> iterator()
    { return this.linkedList.iterator(); }

    // region Package Methods
    boolean add(final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    { return this.linkedList.add(inventoryRecord); }

    java.io.File writeCSV(final java.io.File file)
    {
        if (this.linkedList.isEmpty())
            return null;
        else
        {
            {
                java.io.FileOutputStream fileOutputStream;
                try
                {
                    fileOutputStream = new java.io.FileOutputStream(file); // throws java.io.-
                }                                                          //  FileNotFoundException
                catch (final java.io.FileNotFoundException e) { return null; }

                {
                    final java.io.OutputStreamWriter outputStreamWriter =
                        new java.io.OutputStreamWriter(fileOutputStream);

                    try
                    {
                        outputStreamWriter.append(                     // throws java.io.IOException
                            "box_id,seed_id,inventory_date,inventory_person,weight_gram\r\n");
                        for (final org.wheatgenetics.inventory.model.InventoryRecord
                        inventoryRecord: this.linkedList)
                        {
                            assert inventoryRecord != null;
                            outputStreamWriter.append(inventoryRecord.getCSV()); // throws java.io.-
                        }                                                        //  IOException
                    }
                    catch (final java.io.IOException e) { return null; }

                    try { outputStreamWriter.close(); /* throws java.io.IOException */ }
                    catch (final java.io.IOException e) { return file; }
                }

                try { fileOutputStream.close(); /* throws java.io.IOException */ }
                catch (final java.io.IOException e) { return file; }
            }
            return file;
        }
    }

    java.io.File writeSQL(final java.io.File file, final java.lang.String boxList)
    {
        if (this.linkedList.isEmpty())
            return null;
        else
        {
            {
                java.io.FileOutputStream fileOutputStream;
                try
                {
                    fileOutputStream = new java.io.FileOutputStream(file); // throws java.io.-
                }                                                          //  FileNotFoundException
                catch (final java.io.FileNotFoundException e) { return null; }

                {
                    final java.io.OutputStreamWriter outputStreamWriter =
                        new java.io.OutputStreamWriter(fileOutputStream);

                    try
                    {
                        outputStreamWriter.append(                     // throws java.io.IOException
                            "DELETE FROM seedinv WHERE seedinv.box_id in " + boxList + ";\n");
                        outputStreamWriter.append(                     // throws java.io.IOException
                            "INSERT INTO seedinv(`box_id`,`seed_id`,`inventory_date`," +
                            "`inventory_person`,`weight_gram`)\r\nVALUES");
                        {
                            java.lang.StringBuffer body = new java.lang.StringBuffer();
                            {
                                final int  first = 0, last = this.linkedList.size() - 1;
                                      char terminator = ','                            ;
                                for (int i = first; i <= last; i++)
                                {
                                    if (last == i) terminator = ';';
                                    body.append(
                                        this.linkedList.get(i).getSQL() + terminator + "\r\n");
                                }
                            }
                            outputStreamWriter.append(body.toString());      // throws
                        }                                                    //  java.io.IOException
                    }
                    catch (final java.io.IOException e) { return null; }

                    try { outputStreamWriter.close(); /* throws java.io.IOException */ }
                    catch (final java.io.IOException e) { return file; }
                }
                try { fileOutputStream.close(); /* throws java.io.IOException */ }
                catch (final java.io.IOException e) { return file; }
            }
            return file;
        }
    }

    int sendDebugLogMsg(final java.lang.String tag)
    { return android.util.Log.d(tag, this.toString()); }
    // endregion
}