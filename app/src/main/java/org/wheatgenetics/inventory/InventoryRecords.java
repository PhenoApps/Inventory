package org.wheatgenetics.inventory;

class InventoryRecords extends java.lang.Object
{
    protected java.util.LinkedList<org.wheatgenetics.inventory.InventoryRecord> linkedList =
        new java.util.LinkedList<InventoryRecord>();

    boolean add(final org.wheatgenetics.inventory.InventoryRecord inventoryRecord)
    {
        assert this.linkedList != null;
        return this.linkedList.add(inventoryRecord);
    }

    java.util.Iterator<org.wheatgenetics.inventory.InventoryRecord> iterator()
    {
        assert this.linkedList != null;
        return this.linkedList.iterator();
    }

    java.io.File writeCSV(final java.lang.String filename) throws java.io.IOException
    {
        assert this.linkedList != null;
        if (this.linkedList.isEmpty())
            return null;
        else
        {
            final java.io.File file =
                new java.io.File(org.wheatgenetics.inventory.Constants.MAIN_PATH, filename);
            file.createNewFile();                                      // throws java.io.IOException
            {
                final java.io.FileOutputStream fileOutputStream =
                    new java.io.FileOutputStream(file);
                {
                    final java.io.OutputStreamWriter outputStreamWriter =
                        new java.io.OutputStreamWriter(fileOutputStream);

                    outputStreamWriter.append(
                        "box_id,seed_id,inventory_date,inventory_person,weight_gram\r\n");
                    for (org.wheatgenetics.inventory.InventoryRecord inventoryRecord :
                    this.linkedList)
                        outputStreamWriter.append(inventoryRecord.getCSV());
                    outputStreamWriter.close();
                }
                fileOutputStream.close();
            }
            return file;
        }
    }

    java.io.File writeSQL(final java.lang.String filename,
    final java.lang.String boxList) throws java.io.IOException
    {
        assert this.linkedList != null;
        if (this.linkedList.isEmpty())
            return null;
        else
        {
            final java.io.File file =
                new java.io.File(org.wheatgenetics.inventory.Constants.MAIN_PATH, filename);
            file.createNewFile();                                      // throws java.io.IOException
            {
                final java.io.FileOutputStream fileOutputStream =
                    new java.io.FileOutputStream(file);
                {
                    final java.io.OutputStreamWriter outputStreamWriter =
                        new java.io.OutputStreamWriter(fileOutputStream);

                    outputStreamWriter.append(
                        "DELETE FROM seedinv WHERE seedinv.box_id in " + boxList + ";\n");
                    outputStreamWriter.append("INSERT INTO seedinv(`box_id`,`seed_id`," +
                        "`inventory_date`,`inventory_person`,`weight_gram`)\r\nVALUES");
                    {
                        java.lang.String body = "";
                        {
                            final int last = this.linkedList.size() - 1;
                            for (int i = 0; i <= last; i++)
                                if (i == last)
                                    body += this.linkedList.get(i).getSQL() + ";\r\n";
                                else
                                    body += this.linkedList.get(i).getSQL() + ",\r\n";
                        }
                        outputStreamWriter.append(body);
                    }
                    outputStreamWriter.close();
                }
                fileOutputStream.close();
            }
            return file;
        }
    }
}