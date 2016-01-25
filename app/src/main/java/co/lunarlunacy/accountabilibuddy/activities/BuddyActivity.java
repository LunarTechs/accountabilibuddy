package co.lunarlunacy.accountabilibuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.R;
import co.lunarlunacy.accountabilibuddy.daos.BuddyDAO;
import co.lunarlunacy.accountabilibuddy.models.Buddy;
import co.lunarlunacy.accountabilibuddy.models.adapters.BuddyAdapter;
import co.lunarlunacy.accountabilibuddy.utils.Tags;

public class BuddyActivity extends AppCompatActivity {

    private ArrayList<Buddy> buddies = new ArrayList<>();
    private ListView buddyList;

    private final BuddyDAO buddyDAO = new BuddyDAO(this);

    private EditText buddyNameText;
    private EditText buddyPhoneText;

    private BuddyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buddy);

        // Dynamically format input with phone number styling
        buddyNameText = (EditText) findViewById(R.id.buddy_name_field);
        buddyPhoneText = (EditText) findViewById(R.id.buddy_phone_field);
        buddyPhoneText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // Get buddies and populate list
        buddies = buddyDAO.getAll();
        adapter = new BuddyAdapter(this, R.layout.list_item, buddies);
        buddyList = (ListView) findViewById(R.id.buddy_list);
        buddyList.setAdapter(adapter);

        buddyList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Buddy buddy = (Buddy) buddyList.getAdapter().getItem(position);
                buddyDAO.setCurrent(buddy);

                Intent intent = new Intent();
                intent.putExtra(Tags.CURRENT_BUDDY.getLongName(), buddy);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    /**
     * Called when the user clicks the Add Buddy button. <br>
     * Binds UI again
     */
    public void saveBuddy(View view) {
        // TODO add blank phoneNumber checking
        Buddy buddy = new Buddy(buddyNameText.getText().toString(), buddyPhoneText.getText().toString(), false);
        buddyDAO.insert(buddy);
        buddies.add(buddy);
        adapter.notifyDataSetChanged();
        clearTextFields();
    }

    private void clearTextFields() {
        buddyNameText.setText("");
        buddyPhoneText.setText("");
    }

}
