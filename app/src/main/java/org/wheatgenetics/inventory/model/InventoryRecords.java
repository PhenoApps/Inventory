package org.wheatgenetics.inventory.model;

/**
 * Uses:
 * android.util.Log
 *
 * org.wheatgenetics.inventory.model.InventoryRecord
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class InventoryRecords extends java.lang.Object
implements java.lang.Iterable<org.wheatgenetics.inventory.model.InventoryRecord>
{
    @java.lang.SuppressWarnings({"Convert2Diamond"})
    private final java.util.LinkedList<org.wheatgenetics.inventory.model.InventoryRecord>
        linkedList = new java.util.LinkedList<org.wheatgenetics.inventory.model.InventoryRecord>();

    @java.lang.Override
    public java.util.Iterator<org.wheatgenetics.inventory.model.InventoryRecord> iterator()
    { return this.linkedList.iterator(); }

    // region Public Methods
    public boolean isEmpty() { return this.linkedList.isEmpty(); }

    public boolean add(final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    { return this.linkedList.add(inventoryRecord); }

    public java.io.File writeCSV(final java.io.File file)
    {
        if (this.isEmpty())
            return null;
        else
        {
            {
                final java.io.FileOutputStream fileOutputStream;
                try { fileOutputStream = new java.io.FileOutputStream(file); }   // throws java.io.-
                catch (final java.io.FileNotFoundException e) { return null; }   //  FileNotFoundEx-
                                                                                 //  ception
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

                    try { outputStreamWriter.close() /* throws java.io.IOException */; }
                    catch (final java.io.IOException e) { return file; }
                }

                try { fileOutputStream.close() /* throws java.io.IOException */; }
                catch (final java.io.IOException e) { return file; }
            }
            return file;
        }
    }

    public java.io.File writeSQL(final java.io.File file, final java.lang.String boxList)
    {
        if (this.isEmpty())
            return null;
        else
        {
            {
                final java.io.FileOutputStream fileOutputStream;
                try { fileOutputStream = new java.io.FileOutputStream(file); }   // throws java.io.-
                catch (final java.io.FileNotFoundException e) { return null; }   //  FileNotFoundEx-
                                                                                 //  ception
                {
                    final java.io.OutputStreamWriter outputStreamWriter =
                        new java.io.OutputStreamWriter(fileOutputStream);
                    try
                    {
                        outputStreamWriter.append(                     // throws java.io.IOException
                                "DELETE FROM seedinv WHERE seedinv.box_id in ")
                                    .append(boxList).append(";\n")     // throws java.io.IOException
                            .append(                                   // throws java.io.IOException
                                "INSERT INTO seedinv(`box_id`,`seed_id`,`inventory_date`," +
                                "`inventory_person`,`weight_gram`)\r\nVALUES");
                        {
                            final java.lang.StringBuilder body = new java.lang.StringBuilder();
                            {
                                final int  first = 0, last = this.linkedList.size() - 1;
                                      char terminator = ','                            ;
                                for (int i = first; i <= last; i++)
                                {
                                    if (last == i) terminator = ';';
                                    body.append(this.linkedList.get(i).getSQL())
                                        .append(terminator).append("\r\n");
                                }
                            }
                            outputStreamWriter.append(body.toString());      // throws
                        }                                                    //  java.io.IOException
                    }
                    catch (final java.io.IOException e) { return null; }

                    try { outputStreamWriter.close() /* throws java.io.IOException */; }
                    catch (final java.io.IOException e) { return file; }
                }
                try { fileOutputStream.close() /* throws java.io.IOException */; }
                catch (final java.io.IOException e) { return file; }
            }
            return file;
        }
    }

    public int sendDebugLogMsg(final java.lang.String tag)
    { return android.util.Log.d(tag, this.toString()); }
    // endregion
}