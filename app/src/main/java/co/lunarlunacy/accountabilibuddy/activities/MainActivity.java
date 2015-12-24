package co.lunarlunacy.accountabilibuddy.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.lunarlunacy.accountabilibuddy.R;
import co.lunarlunacy.accountabilibuddy.daos.BuddyDAO;
import co.lunarlunacy.accountabilibuddy.utils.Tags;

public class MainActivity extends ActionBarActivity {

    private final BuddyDAO buddyDAO = new BuddyDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dynamically format input with phone number styling
        EditText editText = (EditText) findViewById(R.id.phone_number_field);
        editText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        bindUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the Send button
     */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.message);
        String message = editText.getText().toString();
        String messageToSend = appendAppName(message);
        sendSMS(buddyDAO.loadPhone(), messageToSend);
    }

    private String appendAppName(String message) {
        return message + "\n" + "\n" + "Sent from AccountabiliBuddy";
    }

    private void sendSMS(String phoneNumber, String message) {
        Intent intent = new Intent(this, SentMessageActivity.class);
        intent.putExtra(Tags.MESSAGE.getShortName(), message);
        intent.putExtra(Tags.PHONE_NUMBER.getShortName(), phoneNumber);

        // TODO add a comment?
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pendingIntent, null);
    }

    /**
     * Called when the user clicks the Save button. <br>
     * Binds UI again
     */
    public void savePhone(View view) {
        String phoneNumber = readPhone();
        // TODO add blank phoneNumber checking
        buddyDAO.savePhone(phoneNumber);
        bindUI();
    }

    /**
     * Called when the user clicks the Clear button <br>
     * Binds UI again
     */
    public void clearPhone(View view) {
        buddyDAO.deletePhone();
        bindUI();
    }

    /**
     * Called when the user hits the Change button
     */
    public void changePhone(View view) {
        LinearLayout phonePrompt = (LinearLayout) findViewById(R.id.phone_form);
        phonePrompt.setVisibility(View.VISIBLE);

        Button changeButton = (Button) findViewById(R.id.change_button);
        changeButton.setVisibility(View.GONE);
        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setVisibility(View.VISIBLE);
    }

    private String readPhone() {
        EditText editText = (EditText) findViewById(R.id.phone_number_field);
        return editText.getText().toString();
    }

    private void bindUI() {
        String phone = buddyDAO.loadPhone();

        TextView savedPhone = (TextView) findViewById(R.id.saved_number);
        LinearLayout phonePrompt = (LinearLayout) findViewById(R.id.phone_form);
        Button changeButton = (Button) findViewById(R.id.change_button);
        EditText message = (EditText) findViewById(R.id.message);
        Button sendButton = (Button) findViewById(R.id.send_button);

        if(phone != null) {
            savedPhone.setText(phone);

            phonePrompt.setVisibility(View.GONE);

            changeButton.setVisibility(View.VISIBLE);

            message.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.VISIBLE);
        } else {
            savedPhone.setText(R.string.none);

            phonePrompt.setVisibility(View.VISIBLE);

            changeButton.setVisibility(View.GONE);

            message.setVisibility(View.INVISIBLE);
            sendButton.setVisibility(View.INVISIBLE);
        }
        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setVisibility(View.GONE);
        EditText phoneTextField = (EditText) phonePrompt.findViewById(R.id.phone_number_field);
        phoneTextField.setText("");
    }

}
