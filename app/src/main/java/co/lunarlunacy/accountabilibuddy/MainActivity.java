package co.lunarlunacy.accountabilibuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static final String PHONE_NUMBER = "co.lunarlunacy.accountabilibuddy.PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.enter_number);
        editText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        updateLabels();
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
    /*public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.prompt_phone);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    /**
     * Called when the user clicks the Save button
     */
    public void savePhone(View view) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PHONE_NUMBER, readPhone());
        editor.commit();
        updateLabels();
    }

    /**
     * Called when the user clicks the Clear button
     */
    public void clearPhone(View view) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(PHONE_NUMBER);
        editor.commit();
        updateLabels();
    }

    private String readPhone() {
        EditText editText = (EditText) findViewById(R.id.enter_number);
        return editText.getText().toString();
    }

    private String loadPhone() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(PHONE_NUMBER, null);
    }

    private void updateLabels() {
        String phone = loadPhone();
        TextView textView = (TextView) findViewById(R.id.phone_number);
        EditText editText = (EditText) findViewById(R.id.enter_number);
        Button button = (Button) findViewById(R.id.clear_button);
        if(phone != null) {
            textView.setText(phone);
            editText.setHint(R.string.change_phone);
            button.setVisibility(View.VISIBLE);
        } else {
            textView.setText(R.string.none);
            editText.setHint(R.string.prompt_phone);
            button.setVisibility(View.GONE);
        }
        editText.setText("");
    }

}
