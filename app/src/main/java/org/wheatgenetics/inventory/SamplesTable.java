package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.database.sqlite.SQLiteOpenHelper
 *
 * org.wheatgenetics.inventory.InventoryRecord
 * org.wheatgenetics.inventory.InventoryRecords
 * org.wheatgenetics.inventory.Utils
 */

class SamplesTable extends java.lang.Object
{
    private static class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper
    {
        private final java.lang.String createStatement, dropStatement;

        SQLiteOpenHelper(final android.content.Context context,
        final java.lang.String createStatement, final java.lang.String dropStatement)
        {
            super(
                /* context => */ context      ,
                /* name    => */ "InventoryDB",
                /* factory => */ null         ,
                /* version => */ 3            );
            this.createStatement = createStatement;
            this.dropStatement   = dropStatement  ;
        }

        @java.lang.Override
        public void onCreate(final android.database.sqlite.SQLiteDatabase db)
        {
            assert null != db;
            db.execSQL(this.createStatement);
        }

        @java.lang.Override
        public void onUpgrade(final android.database.sqlite.SQLiteDatabase db,
        final int oldVersion, final int newVersion)
        {
            assert null != db;
            db.execSQL(this.dropStatement);
            this.onCreate(db);
        }
    }

    // region Private Class Constants
    private static final java.lang.String TABLE_NAME = "samples";
    private static final java.lang.String
        ID_FIELD_NAME     = "id"    , BOX_FIELD_NAME  = "box" , ENVID_FIELD_NAME    = "envid"   ,
        PERSON_FIELD_NAME = "person", DATE_FIELD_NAME = "date", POSITION_FIELD_NAME = "position",
        WT_FIELD_NAME     = "wt"    ;
    // endregion

    // region Private Field
    private final org.wheatgenetics.inventory.SamplesTable.SQLiteOpenHelper sqLiteOpenHelper;
    // endregion

    // region Private Class Method
    private static android.content.ContentValues makeContentValues(
    final org.wheatgenetics.inventory.InventoryRecord inventoryRecord)
    {
        assert null != inventoryRecord;

        final android.content.ContentValues contentValues = new android.content.ContentValues();

        contentValues.put(org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME,
            inventoryRecord.getBox());
        contentValues.put(org.wheatgenetics.inventory.SamplesTable.ENVID_FIELD_NAME,
            inventoryRecord.getEnvId());
        contentValues.put(org.wheatgenetics.inventory.SamplesTable.PERSON_FIELD_NAME,
            inventoryRecord.getPerson());
        contentValues.put(org.wheatgenetics.inventory.SamplesTable.DATE_FIELD_NAME,
            inventoryRecord.getDate());
        contentValues.put(org.wheatgenetics.inventory.SamplesTable.POSITION_FIELD_NAME,
            inventoryRecord.getPosition());
        contentValues.put(org.wheatgenetics.inventory.SamplesTable.WT_FIELD_NAME,
            inventoryRecord.getWt());

        return contentValues;
    }
    // endregion

    // region Private Methods
    private int internalDelete(final java.lang.String whereClause)
    {
        final android.database.sqlite.SQLiteDatabase writableDatabase =
            this.sqLiteOpenHelper.getWritableDatabase();
        assert null != writableDatabase;
        return writableDatabase.delete(
            /* table       => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
            /* whereClause => */ whereClause                                        ,
            /* whereArgs   => */ null                                               );
    }

    private org.wheatgenetics.inventory.InventoryRecord get(final int id)
    {
        final org.wheatgenetics.inventory.InventoryRecord inventoryRecord =
            new org.wheatgenetics.inventory.InventoryRecord();
        {
            android.database.Cursor cursor;
            {
                final java.lang.String columns[] = {
                    org.wheatgenetics.inventory.SamplesTable.ID_FIELD_NAME      ,
                    org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME     ,
                    org.wheatgenetics.inventory.SamplesTable.ENVID_FIELD_NAME   ,
                    org.wheatgenetics.inventory.SamplesTable.PERSON_FIELD_NAME  ,
                    org.wheatgenetics.inventory.SamplesTable.DATE_FIELD_NAME    ,
                    org.wheatgenetics.inventory.SamplesTable.POSITION_FIELD_NAME,
                    org.wheatgenetics.inventory.SamplesTable.WT_FIELD_NAME      };
                final android.database.sqlite.SQLiteDatabase readableDatabase =
                    this.sqLiteOpenHelper.getReadableDatabase();
                assert null != readableDatabase;
                cursor = readableDatabase.query(
                    /* table         => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
                    /* columns       => */ columns                                            ,
                    /* selection     => */
                        " " + org.wheatgenetics.inventory.SamplesTable.ID_FIELD_NAME + " = ?",
                    /* selectionArgs => */ org.wheatgenetics.inventory.Utils.makeStringArray(id),
                    /* groupBy       => */ null                                                 ,
                    /* having        => */ null                                                 ,
                    /* orderBy       => */ null                                                 ,
                    /* limit         => */ null                                                 );
            }
            if (null != cursor)
            {
                if (cursor.moveToFirst()) inventoryRecord.set(
                    /* id       => */ cursor.getString(0),
                    /* box      => */ cursor.getString(1),
                    /* envid    => */ cursor.getString(2),
                    /* person   => */ cursor.getString(3),
                    /* date     => */ cursor.getString(4),
                    /* position => */ cursor.getString(5),
                    /* wt       => */ cursor.getString(6));
                cursor.close();
            }
        }
        inventoryRecord.sendDebugLogMsg("get(" + id + ")");
        return inventoryRecord;
    }

    private int update(final org.wheatgenetics.inventory.InventoryRecord inventoryRecord)
    {
        int i;
        {
            assert null != inventoryRecord;

            final int                           id            = inventoryRecord.getId();
            final android.content.ContentValues contentValues =
                org.wheatgenetics.inventory.SamplesTable.makeContentValues(inventoryRecord);

            contentValues.put(org.wheatgenetics.inventory.SamplesTable.ID_FIELD_NAME, id);
            final android.database.sqlite.SQLiteDatabase writableDatabase =
                this.sqLiteOpenHelper.getWritableDatabase();
            assert null != writableDatabase;
            i = writableDatabase.update(
                /* table       => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
                /* values      => */ contentValues                                      ,
                /* whereClause => */
                    org.wheatgenetics.inventory.SamplesTable.ID_FIELD_NAME + " = ?",
                /* whereArgs => */ org.wheatgenetics.inventory.Utils.makeStringArray(id));
        }
        return i;
    }
    // endregion

    // region Package Methods
    // region Constructor Package Method
    SamplesTable(final android.content.Context context)
    {
        super();

        this.sqLiteOpenHelper = new org.wheatgenetics.inventory.SamplesTable.SQLiteOpenHelper(
            context,
            "CREATE TABLE " + org.wheatgenetics.inventory.SamplesTable.TABLE_NAME + " ( " +
                org.wheatgenetics.inventory.SamplesTable.ID_FIELD_NAME                    +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "                                +
                org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME      + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.ENVID_FIELD_NAME    + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.PERSON_FIELD_NAME   + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.DATE_FIELD_NAME     + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.POSITION_FIELD_NAME + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.WT_FIELD_NAME       + " TEXT)"  ,
            "DROP TABLE IF EXISTS " + org.wheatgenetics.inventory.SamplesTable.TABLE_NAME);
    }
    // endregion

    // region Single-Record Package Methods
    void add(final org.wheatgenetics.inventory.InventoryRecord inventoryRecord)
    {
        assert null != inventoryRecord;
        inventoryRecord.sendDebugLogMsg("add()");

        final android.database.sqlite.SQLiteDatabase writableDatabase =
            this.sqLiteOpenHelper.getWritableDatabase();
        assert null != writableDatabase;
        writableDatabase.insert(
            /* table          => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
            /* nullColumnHack => */ null                                               ,
            /* values         => */
                org.wheatgenetics.inventory.SamplesTable.makeContentValues(inventoryRecord));
    }

    boolean delete(final org.wheatgenetics.inventory.InventoryRecord inventoryRecord)
    {
        assert null != inventoryRecord;
        inventoryRecord.sendDebugLogMsg("delete()");
        return this.internalDelete(org.wheatgenetics.inventory.SamplesTable.POSITION_FIELD_NAME +
            "='" + inventoryRecord.getPositionAsString() + "'") > 0;
    }
    // endregion

    // region Multiple-Record Package Methods
    org.wheatgenetics.inventory.InventoryRecords getAll()
    {
        final org.wheatgenetics.inventory.InventoryRecords inventoryRecords =
            new org.wheatgenetics.inventory.InventoryRecords();
        {
            final android.database.sqlite.SQLiteDatabase readableDatabase =
                this.sqLiteOpenHelper.getReadableDatabase();
            assert null != readableDatabase;
            final android.database.Cursor cursor = readableDatabase.rawQuery(
                "SELECT * FROM " + org.wheatgenetics.inventory.SamplesTable.TABLE_NAME, null);

            if (null != cursor)
            {
                while (cursor.moveToNext())
                    inventoryRecords.add(new org.wheatgenetics.inventory.InventoryRecord(
                        /* id       => */ cursor.getString(0),
                        /* box      => */ cursor.getString(1),
                        /* envid    => */ cursor.getString(2),
                        /* person   => */ cursor.getString(3),
                        /* date     => */ cursor.getString(4),
                        /* position => */ cursor.getString(5),
                        /* wt       => */ cursor.getString(6)));
                cursor.close();
            }
        }
        inventoryRecords.sendDebugLogMsg("getAll()");
        return inventoryRecords;
    }

    java.lang.String getBoxList()
    {
        java.lang.String boxList = null;
        {
            final android.database.sqlite.SQLiteDatabase readableDatabase =
                this.sqLiteOpenHelper.getReadableDatabase();
            assert null != readableDatabase;
            final android.database.Cursor cursor = readableDatabase.query(
                /* distinct => */ true                                               ,
                /* table    => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
                /* columns  => */ org.wheatgenetics.inventory.Utils.makeStringArray(
                    org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME),
                /* selection     => */ null                                                   ,
                /* selectionArgs => */ null                                                   ,
                /* groupBy       => */ org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME,
                /* having        => */ null                                                   ,
                /* orderBy       => */ null                                                   ,
                /* limit         => */ null                                                   );

            if (null != cursor)
            {
                if (cursor.moveToFirst())
                {
                    java.lang.String box = cursor.getString(0);
                    while (null == box)
                        if (cursor.moveToNext()) box = cursor.getString(0); else break;

                    if (null != box)
                    {
                        boxList = "'" + box + "'";
                        while (cursor.moveToNext()) boxList += ",'" + cursor.getString(0) + "'";
                        boxList = "(" + boxList + ")";
                    }
                }
                cursor.close();
            }
        }
        return boxList;
    }

    void deleteAll() { this.internalDelete(null); }
    // endregion
    // endregion
}