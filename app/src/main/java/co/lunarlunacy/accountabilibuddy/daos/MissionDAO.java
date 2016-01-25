package co.lunarlunacy.accountabilibuddy.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.models.Mission;

import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.KEY_CURRENT;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.KEY_DESC;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.KEY_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.TABLE_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission._ID;

/**
 * Created by willepstein on 1/9/16.
 */
public class MissionDAO extends BaseDAO<Mission> {

    public MissionDAO(Context context) {
        super(context);
    }

    @Override
    public Mission get(long missionId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, _ID + "=?", new String[]{Long.toString(missionId)}, null, null, null);

        Mission result = null;
        if(cursor.moveToFirst()) {
            result = read(cursor);
        }

        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<Mission> getAll() {
        ArrayList<Mission> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            result.add(read(cursor));
        }

        cursor.close();
        db.close();
        return result;
    }

    public Mission getCurrent() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_CURRENT + ">0", null, null, null, null);

        if(cursor != null && cursor.moveToFirst()) {
            return read(cursor);
        }
        return null;
    }

    public Mission setCurrent(long id) {
        unsetCurrent();

        Mission newCurrent = get(id);
        if(newCurrent != null) {
            newCurrent.setCurrent(true);
            update(newCurrent);
        }
        return null;
    }

    public Mission setCurrent(Mission mission) {
        return setCurrent(mission.getId());
    }

    private void unsetCurrent() {
        Mission current = getCurrent();
        if(current != null) {
            current.setCurrent(false);
            update(current);
        }
    }

    @Override
    public long insert(Mission mission) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, mission.getName());
        values.put(KEY_DESC, mission.getDescription());
        values.put(KEY_CURRENT, mission.getCurrent());

        long missionId = db.insert(TABLE_NAME, null, values);
        db.close();
        return missionId;
    }

    @Override
    public void update(Mission mission) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ID, mission.getId());
        values.put(KEY_NAME, mission.getName());
        values.put(KEY_DESC, mission.getDescription());
        values.put(KEY_CURRENT, mission.getCurrent());

        db.update(TABLE_NAME, values, _ID + "=?", new String[]{Long.toString(mission.getId())});
        db.close();
    }

    @Override
    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + "=?", new String[]{Long.toString(id)});
        db.close();
    }

    @Override
    public void delete(Mission mission) {
        delete(mission.getId());
    }

    @Override
    protected Mission read(Cursor cursor) {
        Mission mission = new Mission();
        mission.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        mission.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        mission.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
        mission.setCurrent(cursor.getInt(cursor.getColumnIndex(KEY_CURRENT)) > 0);
        return mission;
    }

}
