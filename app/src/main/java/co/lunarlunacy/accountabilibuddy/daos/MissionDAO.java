package co.lunarlunacy.accountabilibuddy.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.models.Mission;
import co.lunarlunacy.accountabilibuddy.providers.DatabaseHelper;

import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.KEY_CURRENT;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.KEY_DESC;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.KEY_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission.TABLE_NAME;
import static co.lunarlunacy.accountabilibuddy.providers.DatabaseContract.Mission._ID;

/**
 * Created by willepstein on 1/9/16.
 */
public class MissionDAO {

    private final Context context;
    private final DatabaseHelper dbHelper;

    public MissionDAO(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public Mission get(long missionId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, _ID + "=?", new String[]{Long.toString(missionId)}, null, null, null);

        Mission result = null;
        if(cursor.moveToFirst()) {
            result = new Mission();
            result.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            result.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
            result.setCurrent(cursor.getInt(cursor.getColumnIndex(KEY_CURRENT)) > 0);
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
            Mission mission = new Mission();
            mission.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            mission.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            mission.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
            mission.setCurrent(cursor.getInt(cursor.getColumnIndex(KEY_CURRENT)) > 0);
            result.add(mission);
        }

        cursor.close();
        db.close();
        return result;
    }

    /**
     * Called when the user clicks the Save button
     * @return new mission id
     */
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

    public long update(Mission mission) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ID, mission.getId());
        values.put(KEY_NAME, mission.getName());
        values.put(KEY_DESC, mission.getDescription());
        values.put(KEY_CURRENT, mission.getCurrent());

        long id = db.update(TABLE_NAME, values, _ID + "=?", new String[]{Long.toString(mission.getId())});
        db.close();
        return id;
    }

    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + "=?", new String[]{Long.toString(id)});
        db.close();
    }

    public void delete(Mission mission) {
        delete(mission.getId());
    }

}
