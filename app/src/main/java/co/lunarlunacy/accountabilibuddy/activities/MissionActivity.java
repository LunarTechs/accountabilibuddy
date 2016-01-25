package co.lunarlunacy.accountabilibuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import co.lunarlunacy.accountabilibuddy.R;
import co.lunarlunacy.accountabilibuddy.daos.MissionDAO;
import co.lunarlunacy.accountabilibuddy.models.Mission;
import co.lunarlunacy.accountabilibuddy.models.adapters.MissionAdapter;
import co.lunarlunacy.accountabilibuddy.utils.Tags;

public class MissionActivity extends AppCompatActivity {

    private ArrayList<Mission> missions = new ArrayList<>();
    private ListView missionList;

    private final MissionDAO missionDAO = new MissionDAO(this);

    private EditText missionNameText;
    private EditText missionDescText;

    private MissionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        missionNameText = (EditText) findViewById(R.id.mission_name_field);
        missionDescText = (EditText) findViewById(R.id.mission_desc_field);

        // Get missions and populate list
        missions = missionDAO.getAll();
        adapter = new MissionAdapter(this, R.layout.list_item, missions);
        missionList = (ListView) findViewById(R.id.mission_list);
        missionList.setAdapter(adapter);

        missionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mission mission = (Mission) missionList.getAdapter().getItem(position);
                missionDAO.setCurrent(mission);

                Intent intent = new Intent();
                intent.putExtra(Tags.CURRENT_MISSION.getLongName(), mission);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    /**
     * Called when the user clicks the Add Buddy button. <br>
     * Binds UI again
     */
    public void saveMission(View view) {
        Mission mission = new Mission(missionNameText.getText().toString(), missionDescText.getText().toString(), false);
        missionDAO.insert(mission);
        missions.add(mission);
        adapter.notifyDataSetChanged();
        clearTextFields();
    }

    private void clearTextFields() {
        missionNameText.setText("");
        missionDescText.setText("");
    }

}
