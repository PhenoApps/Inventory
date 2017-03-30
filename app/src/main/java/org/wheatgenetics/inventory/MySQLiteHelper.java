package org.wheatgenetics.inventory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    //region Constants
    //region Database Constants
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "InventoryDB";
    //endregion


    //region Table Constants
    private static final String TABLE_SAMPLES = "samples";

    private static final String KEY_ID = "id";
    private static final String KEY_BOX = "box";
    private static final String KEY_ENVID = "envid";
    private static final String KEY_PERSON = "person";
    private static final String KEY_DATE = "date";
    private static final String KEY_POSITION = "position";
    private static final String KEY_WT = "wt";

    private static final String[] COLUMNS = {KEY_ID, KEY_BOX, KEY_ENVID,
            KEY_PERSON, KEY_DATE, KEY_POSITION, KEY_WT};
    //endregion
    //endregion



    public MySQLiteHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //region Overridden Methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SAMPLES + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BOX + " TEXT, "
                + KEY_ENVID + " TEXT, " + KEY_PERSON + " TEXT, " + KEY_DATE + " TEXT, "
                + KEY_POSITION + " TEXT, " + KEY_WT + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAMPLES);
        this.onCreate(db);
    }
    //endregion


    //region Protected Methods
    static protected ContentValues makeContentValues(final InventoryRecord sample) {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_BOX     , sample.getBox()     );
        contentValues.put(KEY_ENVID   , sample.getEnvID()   );
        contentValues.put(KEY_PERSON  , sample.getPersonID());
        contentValues.put(KEY_DATE    , sample.getDate()    );
        contentValues.put(KEY_POSITION, sample.getPosition());
        contentValues.put(KEY_WT      , sample.getWt()      );

        return contentValues;
    }

    static protected String[] makeStringArray(final String value) { return new String[]{value}; }

    static protected String[] makeStringArray(final int value) {
        return MySQLiteHelper.makeStringArray(String.valueOf(value));
    }

    protected int delete(final String whereClause) {
        return this.getWritableDatabase().delete(TABLE_SAMPLES, whereClause, null);
    }

    protected int updateSample(final InventoryRecord sample) {
        int i;
        {
            final SQLiteDatabase db = this.getWritableDatabase();
            {
                final int           id     = sample.getId()     ;
                final ContentValues values = makeContentValues(sample);

                values.put(KEY_ID, id);
                i = db.update(TABLE_SAMPLES, values, KEY_ID + " = ?",
                    MySQLiteHelper.makeStringArray(id));
            }
            db.close();
        }
        return i;
    }
    //endregion


    //region Public Methods
    //region Single-Record Public Methods
    public void addSample(final InventoryRecord sample) {
        Log.d("addSample() ", sample.toString());

        final SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_SAMPLES, null, MySQLiteHelper.makeContentValues(sample));
        db.close();
    }

    public InventoryRecord getSample(final int id) {
        final InventoryRecord sample = new InventoryRecord();
        {
            final Cursor cursor = this.getReadableDatabase().query(TABLE_SAMPLES,
                COLUMNS,
                " " + KEY_ID + " = ?",
                MySQLiteHelper.makeStringArray(id),
                null,
                null,
                null,
                null);

            if (cursor != null) {
                cursor.moveToFirst();
                sample.set(
                    /* id       => */ cursor.getString(0),
                    /* box      => */ cursor.getString(1),
                    /* envID    => */ cursor.getString(2),
                    /* personID => */ cursor.getString(3),
                    /* date     => */ cursor.getString(4),
                    /* position => */ cursor.getString(5),
                    /* wt       => */ cursor.getString(6));
                cursor.close();
            }
        }
        Log.d("getSample(" + id + ")", sample.toString());
        return sample;
    }

    public Boolean deleteSample(final InventoryRecord sample) {
        Log.d("deleteSample()", sample.toString());
        return this.delete(KEY_POSITION + "='" + sample.getPositionAsString() + "'") > 0;
    }
    //endregion


    //region Multiple-Record Public Methods
    public List<InventoryRecord> getAllSamples() {
        final List<InventoryRecord> samples = new LinkedList<>();
        {
            final Cursor cursor = this.getWritableDatabase().rawQuery(
                "SELECT  * FROM " + TABLE_SAMPLES, null);

            if (cursor.moveToFirst()) {
                do {
                    final InventoryRecord sample = new InventoryRecord(
                        /* id       => */ cursor.getString(0),
                        /* box      => */ cursor.getString(1),
                        /* envID    => */ cursor.getString(2),
                        /* personID => */ cursor.getString(3),
                        /* date     => */ cursor.getString(4),
                        /* position => */ cursor.getString(5),
                        /* wt       => */ cursor.getString(6));
                    samples.add(sample);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        Log.d("getAllSamples()", samples.toString());
        return samples;
    }

    public String[] getBoxList() {
        final ArrayList<String> boxList = new ArrayList<>();
              String[]          boxArray;
        {
            final Cursor cursor = this.getWritableDatabase().query(true, TABLE_SAMPLES,
                MySQLiteHelper.makeStringArray(KEY_BOX), null, null, KEY_BOX, null, null, null);

            boxArray = new String[cursor.getCount()];
            if (cursor.moveToFirst()) {
                do {
                    boxList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return boxList.toArray(boxArray);
    }

    public void deleteAllSamples() { this.delete(null); }
    //endregion
    //endregion
}