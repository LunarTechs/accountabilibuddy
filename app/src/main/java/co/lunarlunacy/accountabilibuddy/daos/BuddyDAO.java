package co.lunarlunacy.accountabilibuddy.daos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import co.lunarlunacy.accountabilibuddy.utils.Tags;

/**
 * Created by willepstein on 12/21/15.
 */
public class BuddyDAO {

    private static final String PHONE_TAG = Tags.PHONE_NUMBER.getShortName();
    private final Context context;

    public BuddyDAO(Context context) {
        this.context = context;
    }

    public String loadPhone() {
        SharedPreferences sharedPref = context.getSharedPreferences(PHONE_TAG, Context.MODE_PRIVATE);
        return sharedPref.getString(PHONE_TAG, null);
    }

    /**
     * Called when the user clicks the Save button
     */
    public void savePhone(String phoneNumber) {
        SharedPreferences sharedPref = context.getSharedPreferences(PHONE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PHONE_TAG, phoneNumber);
        editor.commit();
    }

    public void deletePhone() {
        SharedPreferences sharedPref = context.getSharedPreferences(PHONE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(PHONE_TAG);
        editor.commit();
    }

}
