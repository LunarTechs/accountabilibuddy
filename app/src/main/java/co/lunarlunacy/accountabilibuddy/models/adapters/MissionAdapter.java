package co.lunarlunacy.accountabilibuddy.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.R;
import co.lunarlunacy.accountabilibuddy.models.Mission;

/**
 * Created by willepstein on 1/23/16.
 */
public class MissionAdapter extends ArrayAdapter<Mission> {

    public MissionAdapter(Context context, int resource, ArrayList<Mission> missions) {
        super(context, resource, missions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Mission mission = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView missionName = (TextView) convertView.findViewById(R.id.list_item_main);
        TextView missionDesc = (TextView) convertView.findViewById(R.id.list_item_subtext);
        // Populate the data into the template view using the data object
        missionName.setText(mission.getName());
        missionDesc.setText(mission.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }

}
