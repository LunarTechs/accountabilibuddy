package co.lunarlunacy.accountabilibuddy.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.models.Buddy;

import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.KEY_CURRENT;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.KEY_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.KEY_PHONE;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy.TABLE_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Buddy._ID;

/**
 * Created by willepstein on 12/21/15.
 */
public class BuddyDAO extends BaseDAO<Buddy> {

    public BuddyDAO(Context context) {
        super(context);
    }

    @Override
    public Buddy get(long buddyId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, _ID + "=?", new String[]{Long.toString(buddyId)}, null, null, null);

        Buddy result = null;
        if(cursor.moveToFirst()) {
            result = read(cursor);
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
            result.add(read(cursor));
        }

        cursor.close();
        db.close();
        return result;
    }

    public Buddy getCurrent() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_CURRENT + ">0", null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            return read(cursor);
        }
        return null;
    }

    public Buddy setCurrent(long id) {
        unsetCurrent();

        Buddy newCurrent = get(id);
        if(newCurrent != null) {
            newCurrent.setCurrent(true);
            update(newCurrent);
        }
        return null;
    }

    public Buddy setCurrent(Buddy buddy) {
        return setCurrent(buddy.getId());
    }

    private void unsetCurrent() {
        Buddy current = getCurrent();
        if(current != null) {
            current.setCurrent(false);
            update(current);
        }
    }

    @Override
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

    @Override
    public void update(Buddy buddy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ID, buddy.getId());
        values.put(KEY_NAME, buddy.getName());
        values.put(KEY_PHONE, buddy.getPhone());
        values.put(KEY_CURRENT, buddy.getCurrent());

        db.update(TABLE_NAME, values, _ID + "=?", new String[]{Long.toString(buddy.getId())});
        db.close();
    }

    @Override
    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + "=?", new String[]{Long.toString(id)});
        db.close();
    }

    @Override
    public void delete(Buddy buddy) {
        delete(buddy.getId());
    }

    @Override
    protected Buddy read(Cursor cursor) {
        Buddy buddy = new Buddy();
        buddy.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        buddy.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        buddy.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
        buddy.setCurrent(cursor.getInt(cursor.getColumnIndex(KEY_CURRENT)) > 0);
        return buddy;
    }

}
