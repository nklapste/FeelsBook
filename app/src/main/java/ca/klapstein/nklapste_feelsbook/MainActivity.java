package ca.klapstein.nklapste_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ListView mFeelsView;
    private CustomListAdapter adapter;
    private ArrayList<FeelCard> feels_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.card_layout);
        setContentView(R.layout.listview_layout);
        mFeelsView = (ListView) findViewById(R.id.feelsview);
        feels_list = new ArrayList<>();
        adapter = new CustomListAdapter(this, R.layout.card_layout, feels_list);
        mFeelsView.setAdapter(adapter);

        // TODO make generic for all buttons
        final Button button_love = findViewById(R.id.button_love);
        button_love.setOnClickListener(this);

        final Button button_surprise = findViewById(R.id.button_surprise);
        button_surprise.setOnClickListener(this);

        final Button button_sadness = findViewById(R.id.button_sadness);
        button_sadness.setOnClickListener(this);

        final Button button_joy = findViewById(R.id.button_joy);
        button_joy.setOnClickListener(this);

        final Button button_fear = findViewById(R.id.button_fear);
        button_fear.setOnClickListener(this);

        final Button button_anger = findViewById(R.id.button_anger);
        button_anger.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        Feel feel = null;
        switch (v.getId()){
            case R.id.button_love:
                feel=Feel.LOVE;
                break;

            case R.id.button_anger:
                feel = Feel.ANGER;
                break;

            case R.id.button_joy:
                feel = Feel.JOY;
                break;

            case R.id.button_sadness:
                feel = Feel.SADNESS;
                break;

            case R.id.button_surprise:
                feel = Feel.SURPRISE;
                break;
            default:
                break;
        }
        feels_list.add(new FeelCard(feel));
        adapter.notifyDataSetChanged();

    }
}
