package co.lunarlunacy.accountabilibuddy.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import co.lunarlunacy.accountabilibuddy.R;

public class SentMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_message);

        Intent intent = getIntent();
        String phone = intent.getStringExtra(MainActivity.PHONE_NUMBER);
        String message = intent.getStringExtra(MainActivity.MESSAGE);

        String title = String.format("%s %s", getResources().getString(R.string.sent_to), phone);
        setTitle(title);

        TextView textView = (TextView) findViewById(R.id.send_message);
        textView.setText(message);
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
}
