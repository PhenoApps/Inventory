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

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INVENTORY_TABLE = "CREATE TABLE samples ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "box TEXT, "
                + "envid TEXT, " + "person TEXT, " + "date TEXT, "
                + "position TEXT, " + "wt TEXT" + ")";

        db.execSQL(CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS samples");
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

    public void addSample(InventoryRecord sample) {
        Log.d("Add Sample: ", sample.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BOX, sample.getBox());
        values.put(KEY_ENVID, sample.getEnvID());
        values.put(KEY_PERSON, sample.getPersonID());
        values.put(KEY_DATE, sample.getDate());
        values.put(KEY_POSITION, sample.getPosition());
        values.put(KEY_WT, sample.getWt());

        db.insert(TABLE_SAMPLES, null,
                values);
        db.close();
    }

    public InventoryRecord getSample(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SAMPLES,
                COLUMNS,
                " id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        InventoryRecord sample = new InventoryRecord();

        if (cursor != null) {
            sample.setId(Integer.parseInt(cursor.getString(0)));
            sample.setBox(cursor.getString(1));
            sample.setEnvID(cursor.getString(2));
            sample.setPersonID(cursor.getString(3));
            sample.setDate(cursor.getString(4));
            sample.setPosition(Integer.parseInt(cursor.getString(5)));
            sample.setWt(cursor.getString(6));
            cursor.close();
        }

        Log.d("getSample(" + id + ")", sample.toString());

        return sample;
    }

    public List<InventoryRecord> getAllSamples() {
        List<InventoryRecord> samples = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_SAMPLES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        InventoryRecord sample;
        if (cursor.moveToFirst()) {
            do {
                sample = new InventoryRecord();
                sample.setId(Integer.parseInt(cursor.getString(0)));
                sample.setBox(cursor.getString(1));
                sample.setEnvID(cursor.getString(2));
                sample.setPersonID(cursor.getString(3));
                sample.setDate(cursor.getString(4));
                sample.setPosition(Integer.parseInt(cursor.getString(5)));
                sample.setWt(cursor.getString(6));

                samples.add(sample);
            } while (cursor.moveToNext());
        }

        cursor.close();

        Log.d("getAllSamples()", samples.toString());

        return samples;
    }

    public int updateSample(InventoryRecord sample) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", sample.getId());
        values.put("box", sample.getBox());
        values.put("envid", sample.getEnvID());
        values.put("person", sample.getPersonID());
        values.put("date", sample.getDate());
        values.put("position", sample.getPosition());
        values.put("wt", sample.getWt());

        int i = db.update(TABLE_SAMPLES,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(sample.getId())});
        db.close();

        return i;
    }

    public Boolean deleteSample(InventoryRecord sample) {
        SQLiteDatabase db = this.getWritableDatabase();
        String num = "'" + Integer.toString(sample.getPosition()) + "'";
        Log.d("deleteSample", sample.toString());
        return db.delete(TABLE_SAMPLES, KEY_POSITION + "=" + num, null) > 0;
    }

    public void deleteAllSamples() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAMPLES, null, null);
    }

    public String[] getBoxList() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_SAMPLES, new String[]{KEY_BOX},
                null, null, KEY_BOX, null, null, null);
        String[] boxes = new String[cursor.getCount()];
        ArrayList<String> arrcurval = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrcurval.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        boxes = arrcurval.toArray(boxes);
        return boxes;
    }
}
