package co.lunarlunacy.accountabilibuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.lunarlunacy.accountabilibuddy.R;
import co.lunarlunacy.accountabilibuddy.connectors.SmsConnector;
import co.lunarlunacy.accountabilibuddy.daos.BuddyDAO;
import co.lunarlunacy.accountabilibuddy.daos.MissionDAO;
import co.lunarlunacy.accountabilibuddy.models.Buddy;
import co.lunarlunacy.accountabilibuddy.models.Mission;
import co.lunarlunacy.accountabilibuddy.utils.Tags;

public class MainActivity extends AppCompatActivity {

    private static final int CURRENT_BUDDY_REQUEST = 1;  // The request code
    private static final int CURRENT_MISSION_REQUEST = 2;  // The request code

    private final BuddyDAO buddyDAO = new BuddyDAO(this);
    private final MissionDAO missionDAO = new MissionDAO(this);
    private final SmsConnector smsConnector = new SmsConnector(this);

    private TextView buddyTextView;
    private TextView missionTextView;
    private Buddy currentBuddy;
    private Mission currentMission;

    private EditText messageText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buddyTextView = (TextView) findViewById(R.id.current_buddy);
        currentBuddy = buddyDAO.getCurrent();
        if(currentBuddy != null) {
            buddyTextView.setText(Html.fromHtml(currentBuddy.getName() + "<br/><small>" + currentBuddy.getPhone() + "</small>"));
        }

        missionTextView = (TextView) findViewById(R.id.current_mission);
        currentMission = missionDAO.getCurrent();
        if(currentMission != null) {
            missionTextView.setText(Html.fromHtml(currentMission.getName() + "<br/><small>" + currentMission.getDescription() + "</small>"));
        }

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setEnabled(isSendButtonEnabled());

        messageText = (EditText) findViewById(R.id.message);
    }

    private boolean isSendButtonEnabled() {
        return currentBuddy != null && currentMission != null;
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
        String message = messageText.getText().toString();
        String messageToSend = appendAppName(message);

        if(currentBuddy == null || currentMission == null) {
            makeErrorToast("Choose a buddy and mission");
            return;
        }
        if(message.isEmpty()) {
            makeErrorToast("Enter a message");
            return;
        }

        smsConnector.sendSMS(currentBuddy.getPhone(), messageToSend);
    }

    //TODO put these toasts somewhere generic
    private void makeErrorToast(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    private String appendAppName(String message) {
        return message + "\n" + "\n" + "Sent from AccountabiliBuddy";
    }

    /**
     * Called when a user clicks the buddy layout
     */
    public void openBuddyActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), BuddyActivity.class);
        startActivityForResult(intent, CURRENT_BUDDY_REQUEST);
    }

    /**
     * Called when a user clicks the mission layout
     */
    public void openMissionActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MissionActivity.class);
        startActivityForResult(intent, CURRENT_MISSION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CURRENT_BUDDY_REQUEST) {
            if (resultCode == RESULT_OK) {
                currentBuddy = (Buddy) data.getParcelableExtra(Tags.CURRENT_BUDDY.getLongName());
                if(currentBuddy != null) {
                    buddyTextView.setText(Html.fromHtml(currentBuddy.getName() + "<br/><small>" + currentBuddy.getPhone() + "</small>"));
                }
                sendButton.setEnabled(isSendButtonEnabled());
            }
        } else if(requestCode == CURRENT_MISSION_REQUEST) {
            if(resultCode == RESULT_OK) {
                currentMission = (Mission) data.getParcelableExtra(Tags.CURRENT_MISSION.getLongName());
                if(currentMission != null) {
                    missionTextView.setText(Html.fromHtml(currentMission.getName() + "<br/><small>" + currentMission.getDescription() + "</small>"));
                }
                sendButton.setEnabled(isSendButtonEnabled());
            }
        }
    }

}
