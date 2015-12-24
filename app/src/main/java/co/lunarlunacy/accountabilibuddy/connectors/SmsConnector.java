package co.lunarlunacy.accountabilibuddy.connectors;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import co.lunarlunacy.accountabilibuddy.activities.SentMessageActivity;
import co.lunarlunacy.accountabilibuddy.utils.Tags;

/**
 * Created by willepstein on 12/24/15.
 */
public class SmsConnector {

    private Context context;

    public SmsConnector(Context context) {
        this.context = context;
    }

    public void sendSMS(String phoneNumber, String message) {
        Intent intent = new Intent(context, SentMessageActivity.class);
        intent.putExtra(Tags.MESSAGE.getShortName(), message);
        intent.putExtra(Tags.PHONE_NUMBER.getShortName(), phoneNumber);

        // TODO add a comment?
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pendingIntent, null);
    }
}
