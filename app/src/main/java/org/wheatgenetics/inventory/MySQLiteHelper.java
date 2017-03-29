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
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "InventoryDB";

    public MySQLiteHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_SAMPLES + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_BOX + " TEXT, "
                + KEY_ENVID + " TEXT, " + KEY_PERSON + " TEXT, " + KEY_DATE + " TEXT, "
                + KEY_POSITION + " TEXT, " + KEY_WT + " TEXT" + ")";

        db.execSQL(CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAMPLES);
        this.onCreate(db);
    }

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

    public void addSample(final InventoryRecord sample) {
        Log.d("addSample() ", sample.toString());
        final SQLiteDatabase db = this.getWritableDatabase();
        {
            final ContentValues values = new ContentValues();

            values.put(KEY_BOX     , sample.getBox()     );
            values.put(KEY_ENVID   , sample.getEnvID()   );
            values.put(KEY_PERSON  , sample.getPersonID());
            values.put(KEY_DATE    , sample.getDate()    );
            values.put(KEY_POSITION, sample.getPosition());
            values.put(KEY_WT      , sample.getWt()      );

            db.insert(TABLE_SAMPLES, null, values);
        }
        db.close();
    }

    public InventoryRecord getSample(final int id) {
        final InventoryRecord sample = new InventoryRecord();
        {
            final Cursor cursor = this.getReadableDatabase().query(TABLE_SAMPLES,
                COLUMNS,
                " " + KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
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

    protected int updateSample(final InventoryRecord sample) {
        int i;
        {
            final SQLiteDatabase db = this.getWritableDatabase();
            {
                final ContentValues values = new ContentValues();
                final int           id     = sample.getId()     ;

                values.put(KEY_ID      , id                  );
                values.put(KEY_BOX     , sample.getBox()     );
                values.put(KEY_ENVID   , sample.getEnvID()   );
                values.put(KEY_PERSON  , sample.getPersonID());
                values.put(KEY_DATE    , sample.getDate()    );
                values.put(KEY_POSITION, sample.getPosition());
                values.put(KEY_WT      , sample.getWt()      );

                i = db.update(TABLE_SAMPLES,
                        values,
                        KEY_ID + " = ?",
                        new String[]{String.valueOf(id)});
            }
            db.close();
        }
        return i;
    }

    public Boolean deleteSample(final InventoryRecord sample) {
        Log.d("deleteSample()", sample.toString());
        final SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SAMPLES,
            KEY_POSITION + "='" + sample.getPositionAsString() + "'", null) > 0;
    }

    public void deleteAllSamples() {
        this.getWritableDatabase().delete(TABLE_SAMPLES, null, null);
    }

    public String[] getBoxList() {
        final SQLiteDatabase db = this.getWritableDatabase();
        final Cursor cursor = db.query(true, TABLE_SAMPLES, new String[]{KEY_BOX},
                null, null, KEY_BOX, null, null, null);
        final String[] boxes = new String[cursor.getCount()];
        final ArrayList<String> arrcurval = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrcurval.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return arrcurval.toArray(boxes);
    }
}