package co.lunarlunacy.accountabilibuddy.providers;

import android.provider.BaseColumns;

/**
 * Created by willepstein on 12/27/15.
 */
public class DatabaseContract {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "accountabilibuddy.db";

    // Don't instantiate this class
    private DatabaseContract() {}

    public static abstract class Buddy implements BaseColumns {
        public static final String TABLE_NAME = "buddy";
        public static final String KEY_NAME = "name";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_CURRENT = "current";

        // Don't instantiate
        private Buddy() {}

        /**
         * CREATE TABLE buddy (                         <br>
         *      _id INTEGER PRIMARY KEY AUTOINCREMENT,  <br>
         *      name TEXT NOT NULL,                     <br>
         *      phone TEXT NOT NULL,                    <br>
         *      current INTEGER NOT NULL DEFAULT 0      <br>
         * );
         */
        public static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_PHONE + " TEXT NOT NULL, "
                + KEY_CURRENT + " INTEGER NOT NULL DEFAULT 0)";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Mission implements BaseColumns {
        public static final String TABLE_NAME = "mission";
        public static final String KEY_NAME = "name";
        public static final String KEY_DESC = "description";
        public static final String KEY_CURRENT = "current";

        // Don't instantiate
        private Mission() {}

        /**
         * CREATE TABLE mission (                       <br>
         *      _id INTEGER PRIMARY KEY AUTOINCREMENT,  <br>
         *      name TEXT NOT NULL,                     <br>
         *      description TEXT,                       <br>
         *      current INTEGER NOT NULL DEFAULT 0      <br>
         * );
         */
        public static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_DESC + " TEXT NOT NULL, "
                + KEY_CURRENT + " INTEGER NOT NULL DEFAULT 0)";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class MissionBuddy implements  BaseColumns {
        public static final String TABLE_NAME = "mission_buddy";
        public static final String KEY_CREATE_DT = "create_dt";
        public static final String KEY_ACTIVE = "active";
        public static final String KEY_MISSION_ID = "mission_id";
        public static final String KEY_BUDDY_ID = "buddy_id";

        // Don't instantiate
        private MissionBuddy() {}

        /**
         * CREATE TABLE mission_buddy (
         *      _id INTEGER PRIMARY KEY AUTOINCREMENT,          <br>
         *      create_dt TEXT NOT NULL,                        <br>
         *      active INTEGER NOT NULL DEFAULT 1,              <br>
         *      mission_id INTEGER,                             <br>
         *      buddy_id INTEGER,                               <br>
         *      FOREIGN KEY(mission_id) REFERENCES mission(id), <br>
         *      FOREIGN KEY(buddy_id) REFERENCES buddy(id)      <br>
         * );
         */
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_CREATE_DT + " TEXT NOT NULL, "
                + KEY_ACTIVE + " INTEGER NOT NULL DEFAULT 1, "
                + KEY_MISSION_ID + " INTEGER, "
                + KEY_BUDDY_ID + " INTEGER, "
                + "FOREIGN KEY(" + KEY_MISSION_ID + ") REFERENCES " + Mission.TABLE_NAME + "(" + _ID + "), "
                + "FOREIGN KEY(" + KEY_BUDDY_ID + ") REFERENCES " + Buddy.TABLE_NAME + "(" + _ID + ")"
                + ")";

        public  static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
