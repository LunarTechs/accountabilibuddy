package co.lunarlunacy.accountabilibuddy.daos;

import android.content.Context;
import android.database.Cursor;

import co.lunarlunacy.accountabilibuddy.providers.DatabaseHelper;

/**
 * Created by willepstein on 1/23/16.
 */
abstract public class BaseDAO<T> implements CRUD<T> {

    protected final Context context;
    protected final DatabaseHelper dbHelper;

    protected BaseDAO(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    abstract T read(Cursor cursor);

}
