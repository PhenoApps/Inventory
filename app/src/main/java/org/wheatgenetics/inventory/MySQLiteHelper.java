package org.wheatgenetics.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    // region Constants
    // region Database Constants
    private static final int    DATABASE_VERSION = 3            ;
    private static final String DATABASE_NAME    = "InventoryDB";
    // endregion


    // region Table Constants
    private static final String TABLE_NAME = "samples";

    private static final String ID_FIELD_NAME       = "id"      ;
    private static final String BOX_FIELD_NAME      = "box"     ;
    private static final String ENVID_FIELD_NAME    = "envid"   ;
    private static final String PERSON_FIELD_NAME   = "person"  ;
    private static final String DATE_FIELD_NAME     = "date"    ;
    private static final String POSITION_FIELD_NAME = "position";
    private static final String WT_FIELD_NAME       = "wt"      ;
    // endregion
    // endregion


    MySQLiteHelper(final Context context) {
        super(context, org.wheatgenetics.inventory.MySQLiteHelper.DATABASE_NAME,
            null, org.wheatgenetics.inventory.MySQLiteHelper.DATABASE_VERSION);
    }


    // region Overridden Methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + this.TABLE_NAME + " ( "                  +
            this.ID_FIELD_NAME       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            this.BOX_FIELD_NAME      + " TEXT, "                              +
            this.ENVID_FIELD_NAME    + " TEXT, "                              +
            this.PERSON_FIELD_NAME   + " TEXT, "                              +
            this.DATE_FIELD_NAME     + " TEXT, "                              +
            this.POSITION_FIELD_NAME + " TEXT, "                              +
            this.WT_FIELD_NAME       + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.TABLE_NAME);
        this.onCreate(db);
    }
    // endregion


    // region Protected Methods
    static protected ContentValues makeContentValues(final InventoryRecord inventoryRecord) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(org.wheatgenetics.inventory.MySQLiteHelper.BOX_FIELD_NAME,
            inventoryRecord.getBox());
        contentValues.put(org.wheatgenetics.inventory.MySQLiteHelper.ENVID_FIELD_NAME,
            inventoryRecord.getEnvId());
        contentValues.put(org.wheatgenetics.inventory.MySQLiteHelper.PERSON_FIELD_NAME,
            inventoryRecord.getPerson());
        contentValues.put(org.wheatgenetics.inventory.MySQLiteHelper.DATE_FIELD_NAME,
            inventoryRecord.getDate());
        contentValues.put(org.wheatgenetics.inventory.MySQLiteHelper.POSITION_FIELD_NAME,
            inventoryRecord.getPosition());
        contentValues.put(org.wheatgenetics.inventory.MySQLiteHelper.WT_FIELD_NAME,
            inventoryRecord.getWt());

        return contentValues;
    }

    static protected String[] makeStringArray(final String value) { return new String[]{value}; }

    static protected String[] makeStringArray(final int value) {
        return MySQLiteHelper.makeStringArray(String.valueOf(value));
    }

    protected int delete(final String whereClause) {
        return this.getWritableDatabase().delete(this.TABLE_NAME, whereClause, null);
    }

    protected int updateInventoryRecord(final InventoryRecord inventoryRecord) {
        int i;
        {
            final SQLiteDatabase db = this.getWritableDatabase();
            {
                final int           id            = inventoryRecord.getId();
                final ContentValues contentValues =
                    org.wheatgenetics.inventory.MySQLiteHelper.makeContentValues(inventoryRecord);

                contentValues.put(this.ID_FIELD_NAME, id);
                i = db.update(this.TABLE_NAME, contentValues, this.ID_FIELD_NAME + " = ?",
                    org.wheatgenetics.inventory.MySQLiteHelper.makeStringArray(id));
            }
            db.close();
        }
        return i;
    }
    // endregion


    // region Package Methods
    // region Single-Record Package Methods
    void addInventoryRecord(final InventoryRecord inventoryRecord) {
        Log.d("addInventoryRecord() ", inventoryRecord.toString());

        final SQLiteDatabase db = this.getWritableDatabase();

        db.insert(this.TABLE_NAME, null,
            org.wheatgenetics.inventory.MySQLiteHelper.makeContentValues(inventoryRecord));
        db.close();
    }

    InventoryRecord getInventoryRecord(final int id) {
        final InventoryRecord inventoryRecord = new InventoryRecord();
        {
            final String[] FIELD_NAMES = {
                org.wheatgenetics.inventory.MySQLiteHelper.ID_FIELD_NAME      ,
                org.wheatgenetics.inventory.MySQLiteHelper.BOX_FIELD_NAME     ,
                org.wheatgenetics.inventory.MySQLiteHelper.ENVID_FIELD_NAME   ,
                org.wheatgenetics.inventory.MySQLiteHelper.PERSON_FIELD_NAME  ,
                org.wheatgenetics.inventory.MySQLiteHelper.DATE_FIELD_NAME    ,
                org.wheatgenetics.inventory.MySQLiteHelper.POSITION_FIELD_NAME,
                org.wheatgenetics.inventory.MySQLiteHelper.WT_FIELD_NAME      };
            final Cursor cursor = this.getReadableDatabase().query(
                /* table         => */ this.TABLE_NAME                  ,
                /* columns       => */ FIELD_NAMES                      ,
                /* selection     => */ " " + this.ID_FIELD_NAME + " = ?",
                /* selectionArgs => */
                    org.wheatgenetics.inventory.MySQLiteHelper.makeStringArray(id),
                /* groupBy => */ null,
                /* having  => */ null,
                /* orderBy => */ null,
                /* limit   => */ null);

            if (cursor != null) {
                cursor.moveToFirst();
                inventoryRecord.set(
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
        Log.d("getInventoryRecord(" + id + ")", inventoryRecord.toString());
        return inventoryRecord;
    }

    Boolean deleteInventoryRecord(final InventoryRecord inventoryRecord) {
        Log.d("deleteInventoryRecord()", inventoryRecord.toString());
        return this.delete(
            this.POSITION_FIELD_NAME + "='" + inventoryRecord.getPositionAsString() + "'") > 0;
    }
    // endregion


    // region Multiple-Record Package Methods
    InventoryRecords getInventoryRecords() {
        final InventoryRecords inventoryRecords = new InventoryRecords();
        {
            final Cursor cursor = this.getWritableDatabase().rawQuery(
                "SELECT * FROM " + this.TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    inventoryRecords.add(new InventoryRecord(
                        /* id       => */ cursor.getString(0),
                        /* box      => */ cursor.getString(1),
                        /* envid    => */ cursor.getString(2),
                        /* person   => */ cursor.getString(3),
                        /* date     => */ cursor.getString(4),
                        /* position => */ cursor.getString(5),
                        /* wt       => */ cursor.getString(6)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        Log.d("getInventoryRecords()", inventoryRecords.toString());
        return inventoryRecords;
    }

    String getBoxList()
    {
        java.lang.String boxList = null;
        {
            final Cursor cursor = this.getWritableDatabase().query(
                /* distinct      => */ true                                               ,
                /* table         => */ this.TABLE_NAME                                    ,
                /* columns       => */ MySQLiteHelper.makeStringArray(this.BOX_FIELD_NAME),
                /* selection     => */ null                                               ,
                /* selectionArgs => */ null                                               ,
                /* groupBy       => */ this.BOX_FIELD_NAME                                ,
                /* having        => */ null                                               ,
                /* orderBy       => */ null                                               ,
                /* limit         => */ null                                               );

            if (cursor.moveToFirst())
            {
                java.lang.String box = cursor.getString(0);
                while (box == null)
                    if (cursor.moveToNext())
                        box = cursor.getString(0);
                    else
                        break;

                if (box != null)
                {
                    boxList = "'" + box + "'";
                    while (cursor.moveToNext()) boxList += ",'" + cursor.getString(0) + "'";
                    boxList = "(" + boxList + ")";
                }
            }
            cursor.close();
        }
        return boxList;
    }

    void deleteInventoryRecords() { this.delete(null); }
    // endregion
    // endregion
}