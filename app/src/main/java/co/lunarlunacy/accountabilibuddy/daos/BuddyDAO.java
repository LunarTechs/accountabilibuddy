package co.lunarlunacy.accountabilibuddy.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.models.Buddy;
import co.lunarlunacy.accountabilibuddy.providers.DatabaseHelper;

import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.KEY_CURRENT;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.KEY_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.KEY_PHONE;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.TABLE_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy._ID;

/**
 * Created by willepstein on 12/21/15.
 */
public class BuddyDAO {

    private final Context context;
    private final DatabaseHelper dbHelper;

    public BuddyDAO(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public Buddy get(long buddyId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, _ID + "=?", new String[]{Long.toString(buddyId)}, null, null, null);

        Buddy result = null;
        if(cursor.moveToFirst()) {
            result = new Buddy();
            result.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            result.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            result.setCurrent(cursor.getInt(cursor.getColumnIndex(KEY_CURRENT)) > 0);
        }

        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<Buddy> getAll() {
        ArrayList<Buddy> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            Buddy buddy = new Buddy();
            buddy.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            buddy.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            buddy.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            buddy.setCurrent(cursor.getInt(cursor.getColumnIndex(KEY_CURRENT)) > 0);
            result.add(buddy);
        }

        cursor.close();
        db.close();
        return result;
    }

    /**
     * Called when the user clicks the Save button
     * @return new buddy id
     */
    public long insert(Buddy buddy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, buddy.getName());
        values.put(KEY_PHONE, buddy.getPhone());
        values.put(KEY_CURRENT, buddy.getCurrent());

        long buddyId = db.insert(TABLE_NAME, null, values);
        db.close();
        return buddyId;
    }

    public long update(Buddy buddy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ID, buddy.getId());
        values.put(KEY_NAME, buddy.getName());
        values.put(KEY_PHONE, buddy.getPhone());
        values.put(KEY_CURRENT, buddy.getCurrent());

        long id = db.update(TABLE_NAME, values, _ID + "=?", new String[]{Long.toString(buddy.getId())});
        db.close();
        return id;
    }

    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + "=?", new String[]{Long.toString(id)});
        db.close();
    }

    public void delete(Buddy buddy) {
        delete(buddy.getId());
    }

}
