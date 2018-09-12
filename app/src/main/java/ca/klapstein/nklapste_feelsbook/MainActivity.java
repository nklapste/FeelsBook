package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import java.text.ParseException;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final int EDIT_COMMENT_REQUEST_CODE = 1;
    private FeelAdapter mAdapter;
    private FeelList mFeelList;

    @Override
    protected void onDestroy() {
        PreferencesManager.saveSharedPreferencesFeelList(getApplicationContext(), mFeelList);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeelList = PreferencesManager.loadSharedPreferencesFeelList(getApplicationContext());

        setContentView(R.layout.listview_layout);
        RecyclerView mFeelsRecyclerView = (RecyclerView) findViewById(R.id.feels_recycler_view);

        mFeelsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFeelsRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FeelAdapter(mFeelList);
        mFeelsRecyclerView.setAdapter(mAdapter);
        mFeelsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mFeelsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            /**
             * Create a popup menu on a long click of a Feel.
             *
             * This menu provides two options:
             *      1. Edit the Feel.
             *      2. Delete the Feel.
             *
             * @param view {@code View}
             * @param position {@code int}
             */
            @Override
            public void onLongClick(View view, final int position) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                //inflating menu from xml resource
                popup.inflate(R.menu.feel_options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.button_delete:
                                mFeelList.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                mAdapter.notifyItemRangeChanged(position, mFeelList.size());
                                return true;
                            case R.id.button_edit_feeling:
                                Intent intent = new Intent(getApplicationContext(), EditFeelActivity.class);
                                intent.putExtra("date", dateFormat.format(mFeelList.get(position).getDate()));
                                intent.putExtra("feeling", mFeelList.get(position).getFeeling());
                                intent.putExtra("comment", mFeelList.get(position).getComment());
                                intent.putExtra("position", position);
                                startActivityForResult(intent, 1);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        }));

        // set the OnClick function for the feeling buttons
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

        // set the OnClick function for the Stats button
        final Button button_stats = findViewById(R.id.button_stats);
        button_stats.setOnClickListener(new View.OnClickListener() {

            /**
             * Compute the tally of all feelings within mFeelList and send the totals to
             * StatsActivity to be displayed.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
                intent.putExtra("angerTally", mFeelList.getAngerTally());
                intent.putExtra("fearTally", mFeelList.getFearTally());
                intent.putExtra("joyTally", mFeelList.getJoyTally());
                intent.putExtra("loveTally", mFeelList.getLoveTally());
                intent.putExtra("sadnessTally", mFeelList.getSadnessTally());
                intent.putExtra("surpriseTally", mFeelList.getSurpriseTally());
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EDIT_COMMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            Date date = null;
            try {
                date = dateFormat.parse(intent.getStringExtra("date"));
            } catch (ParseException e) {
                Log.e(TAG, "Failed to parse date: " + intent.getStringExtra("date"), e);
            }
            String feeling = intent.getStringExtra("feeling");
            String comment = intent.getStringExtra("comment");
            final int position = intent.getIntExtra("position", 0);

            mFeelList.remove(position);
            Feel feel = new Feel(feeling);
            feel.setComment(comment);
            feel.setDate(date);
            mFeelList.add(feel);
            mAdapter.notifyDataSetChanged();
            PreferencesManager.saveSharedPreferencesFeelList(getApplicationContext(), mFeelList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        String feeling;
        switch (view.getId()) {
            case R.id.button_anger:
                feeling = Feel.ANGER;
                break;

            case R.id.button_fear:
                feeling = Feel.FEAR;
                break;

            case R.id.button_joy:
                feeling = Feel.JOY;
                break;

            case R.id.button_love:
                feeling = Feel.LOVE;
                break;

            case R.id.button_sadness:
                feeling = Feel.SADNESS;
                break;

            case R.id.button_surprise:
                feeling = Feel.SURPRISE;
                break;

            default:
                Log.e(TAG, "Unsupported button pushed: " + view.getId());
                return;
        }
        mFeelList.add(new Feel(feeling));
        mAdapter.notifyDataSetChanged();
        PreferencesManager.saveSharedPreferencesFeelList(getApplicationContext(), mFeelList);
    }
}
