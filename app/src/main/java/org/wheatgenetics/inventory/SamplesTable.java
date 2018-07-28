package org.wheatgenetics.inventory;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 * android.database.sqlite.SQLiteDatabase
 * android.database.sqlite.SQLiteOpenHelper
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.inventory.model.InventoryRecord
 * org.wheatgenetics.inventory.model.InventoryRecords
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
class SamplesTable extends java.lang.Object
{
    private static class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper
    {
        private final java.lang.String createStatement, dropStatement;

        private SQLiteOpenHelper(final android.content.Context context,
        final java.lang.String createStatement, final java.lang.String dropStatement)
        {
            super(
                /* context => */ context,
                /* name    => */"InventoryDB",
                /* factory => */null,
                /* version => */3);

            this.createStatement = createStatement; this.dropStatement = dropStatement;
        }

        @java.lang.Override public void onCreate(final android.database.sqlite.SQLiteDatabase db)
        { assert null != db; db.execSQL(this.createStatement); }

        @java.lang.Override public void onUpgrade(
        final android.database.sqlite.SQLiteDatabase db, final int oldVersion, final int newVersion)
        { assert null != db; db.execSQL(this.dropStatement); this.onCreate(db); }
    }

    // region Constants
    private static final java.lang.String TABLE_NAME = "samples";
    private static final java.lang.String
        ID_FIELD_NAME     = "id"    , BOX_FIELD_NAME  = "box" , ENVID_FIELD_NAME    = "envid"   ,
        PERSON_FIELD_NAME = "person", DATE_FIELD_NAME = "date", POSITION_FIELD_NAME = "position",
        WT_FIELD_NAME     = "wt"    ;
    // endregion

    private final org.wheatgenetics.inventory.SamplesTable.SQLiteOpenHelper sqLiteOpenHelper;

    // region Private Methods
    private static android.content.ContentValues makeContentValues(
    final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    {
        final android.content.ContentValues contentValues = new android.content.ContentValues();

        assert null != inventoryRecord;
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

    private int internalDelete(final java.lang.String whereClause)
    {
        final android.database.sqlite.SQLiteDatabase writableDatabase =
            this.sqLiteOpenHelper.getWritableDatabase();
        assert null != writableDatabase; return writableDatabase.delete(
            /* table       => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
            /* whereClause => */ whereClause,
            /* whereArgs   => */null);
    }
    // endregion

    SamplesTable(final android.content.Context context)
    {
        super();

        this.sqLiteOpenHelper = new org.wheatgenetics.inventory.SamplesTable.SQLiteOpenHelper(
            context,
            "CREATE TABLE " + org.wheatgenetics.inventory.SamplesTable.TABLE_NAME +
                    " ( " +
                org.wheatgenetics.inventory.SamplesTable.ID_FIELD_NAME                    +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "                                +
                org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME      + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.ENVID_FIELD_NAME    + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.PERSON_FIELD_NAME   + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.DATE_FIELD_NAME     + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.POSITION_FIELD_NAME + " TEXT, "  +
                org.wheatgenetics.inventory.SamplesTable.WT_FIELD_NAME       + " TEXT)"  ,
            "DROP TABLE IF EXISTS " +
                org.wheatgenetics.inventory.SamplesTable.TABLE_NAME);
    }

    @java.lang.Override protected void finalize() throws java.lang.Throwable
    { this.sqLiteOpenHelper.close(); super.finalize(); }

    // region Package Methods
    // region Single-Record Package Methods
    void add(final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    {
        assert null != inventoryRecord; inventoryRecord.sendDebugLogMsg("add()");

        final android.database.sqlite.SQLiteDatabase writableDatabase =
            this.sqLiteOpenHelper.getWritableDatabase();
        assert null != writableDatabase; writableDatabase.insert(
            /* table          => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
            /* nullColumnHack => */null,
            /* values         => */
                org.wheatgenetics.inventory.SamplesTable.makeContentValues(inventoryRecord));
    }

    boolean delete(final org.wheatgenetics.inventory.model.InventoryRecord inventoryRecord)
    {
        assert null != inventoryRecord; inventoryRecord.sendDebugLogMsg("delete()");
        return this.internalDelete(
            org.wheatgenetics.inventory.SamplesTable.POSITION_FIELD_NAME +
                "='" + inventoryRecord.getPositionAsString() + "'") > 0;
    }
    // endregion

    // region Multiple-Record Package Methods
    org.wheatgenetics.inventory.model.InventoryRecords getAll()
    {
        final org.wheatgenetics.inventory.model.InventoryRecords inventoryRecords =
            new org.wheatgenetics.inventory.model.InventoryRecords();
        {
            final android.database.Cursor cursor;
            {
                final android.database.sqlite.SQLiteDatabase readableDatabase =
                    this.sqLiteOpenHelper.getReadableDatabase();
                assert null != readableDatabase; cursor = readableDatabase.rawQuery(
                    "SELECT * FROM " + org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
                    null);
            }

            if (null != cursor)
            {
                while (cursor.moveToNext())
                    inventoryRecords.add(new org.wheatgenetics.inventory.model.InventoryRecord(
                        /* box      => */ cursor.getString(1),
                        /* envid    => */ cursor.getString(2),
                        /* person   => */ cursor.getString(3),
                        /* date     => */ cursor.getString(4),
                        /* position => */ cursor.getString(5),
                        /* wt       => */ cursor.getString(6)));
                cursor.close();
            }
        }
        inventoryRecords.sendDebugLogMsg("getAll()"); return inventoryRecords;
    }

    java.lang.String getBoxList()
    {
        java.lang.StringBuilder boxList = null;
        {
            final android.database.Cursor cursor;
            {
                final android.database.sqlite.SQLiteDatabase readableDatabase =
                    this.sqLiteOpenHelper.getReadableDatabase();
                assert null != readableDatabase; cursor = readableDatabase.query(
                    /* distinct => */true,
                    /* table    => */ org.wheatgenetics.inventory.SamplesTable.TABLE_NAME,
                    /* columns  => */ org.wheatgenetics.javalib.Utils.stringArray(
                        org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME),
                    /* selection     => */null,
                    /* selectionArgs => */null,
                    /* groupBy       => */ org.wheatgenetics.inventory.SamplesTable.BOX_FIELD_NAME,
                    /* having        => */null,
                    /* orderBy       => */null,
                    /* limit         => */null);
            }

            if (null != cursor)
            {
                if (cursor.moveToFirst())
                {
                    java.lang.String box = cursor.getString(0);
                    while (null == box)
                        if (cursor.moveToNext()) box = cursor.getString(0); else break;

                    if (null != box)
                    {
                        boxList = new java.lang.StringBuilder("'" + box + "'");
                        while (cursor.moveToNext())
                            boxList.append(",'").append(cursor.getString(0)).append("'");
                        boxList.insert(0,"(").append(")");
                    }
                }
                cursor.close();
            }
        }
        return null == boxList ? null : boxList.toString();
    }

    void deleteAll() { this.internalDelete(null); }
    // endregion
    // endregion
}