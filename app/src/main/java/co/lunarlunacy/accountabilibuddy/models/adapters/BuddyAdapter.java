package co.lunarlunacy.accountabilibuddy.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.R;
import co.lunarlunacy.accountabilibuddy.models.Buddy;

/**
 * Created by willepstein on 1/18/16.
 */
public class BuddyAdapter extends ArrayAdapter<Buddy> {

    public BuddyAdapter(Context context, int resource, ArrayList<Buddy> buddies) {
        super(context, resource, buddies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Buddy buddy = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView buddyName = (TextView) convertView.findViewById(R.id.list_item_main);
        TextView buddyPhone = (TextView) convertView.findViewById(R.id.list_item_subtext);
        // Populate the data into the template view using the data object
        buddyName.setText(buddy.getName());
        buddyPhone.setText(buddy.getPhone());

        // Return the completed view to render on screen
        return convertView;
    }
}
