package co.lunarlunacy.accountabilibuddy.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by willepstein on 12/25/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        deleteTables(db);
        db.execSQL(DatabaseContract.Buddy.CREATE_TABLE);
        db.execSQL(DatabaseContract.Mission.CREATE_TABLE);
        db.execSQL(DatabaseContract.MissionBuddy.CREATE_TABLE);
    }

    // TODO should be changed eventually
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // TODO should be changed eventually
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.MissionBuddy.DELETE_TABLE);
        db.execSQL(DatabaseContract.Buddy.DELETE_TABLE);
        db.execSQL(DatabaseContract.Mission.DELETE_TABLE);
    }
}
