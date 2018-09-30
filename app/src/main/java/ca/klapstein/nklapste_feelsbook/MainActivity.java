package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.os.Bundle;
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

import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Anger;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Fear;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Joy;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Love;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Sadness;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Surprise;
import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final int EDIT_COMMENT_REQUEST_CODE = 1;
    private FeelAdapter mFeelAdapter;
    private FeelQueue mFeelQueue;
    private RecyclerView mFeelsRecyclerView;

    /**
     * Save the MainActivity's {@code FeelQueue} on closing.
     */
    @Override
    protected void onDestroy() {
        FeelsBookPreferencesManager.saveSharedPreferencesFeelList(getApplicationContext(), mFeelQueue);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        mFeelsRecyclerView = findViewById(R.id.feels_recycler_view);
        mFeelsRecyclerView.setHasFixedSize(true);
        mFeelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set the onClick and onLongClick functions for the RecycleView
        mFeelsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mFeelsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {}

            @Override
            public void onLongClick(View view, int position) {
                onFeelListItemClick(view, position);
            }
        }));

        // set the OnClick function for the Stats button
        final Button button_stats = findViewById(R.id.button_stats);
        button_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStatsButtonClick();
            }
        });

        // set the OnClick function for the Feeling buttons
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
    private void onFeelListItemClick(View view, final int position){
        //creating a popup menu
        PopupMenu popup = new PopupMenu(getApplicationContext(), view);

        final Feel feel = (Feel) mFeelQueue.toArray()[position];
        //inflating menu from xml resource
        popup.inflate(R.menu.feel_options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.button_delete:
                        mFeelQueue.remove(feel);
                        mFeelAdapter.notifyItemRemoved(position);
                        mFeelAdapter.notifyItemRangeChanged(position, mFeelQueue.size());
                        return true;
                    case R.id.button_edit_feeling:
                        Intent intent = new Intent(getApplicationContext(), EditFeelActivity.class);
                        intent.putExtra("date", dateFormat.format(feel.getDate()));
                        intent.putExtra("feeling", feel.getFeeling().toString());
                        intent.putExtra("comment", feel.getComment());
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

    /**
     * Get the tally of all feelings within mFeelQueue and send the totals to
     * StatsActivity to be displayed.
     */
    private void onStatsButtonClick(){
        Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
        intent.putExtra("angerTally", mFeelQueue.getAngerTally());
        intent.putExtra("fearTally", mFeelQueue.getFearTally());
        intent.putExtra("joyTally", mFeelQueue.getJoyTally());
        intent.putExtra("loveTally", mFeelQueue.getLoveTally());
        intent.putExtra("sadnessTally", mFeelQueue.getSadnessTally());
        intent.putExtra("surpriseTally", mFeelQueue.getSurpriseTally());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EDIT_COMMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            onEditFeelActivityResult(intent);
        }
    }

    /**
     * After getting the results from an EditFeelActivity modify the {@code mFeelQueue} to
     * represent the changes made to the selected feel.
     *
     * @param intent {@code Intent}
     */
    private void onEditFeelActivityResult(Intent intent){
        Date date;
        try {
            date = dateFormat.parse(intent.getStringExtra("date"));
        } catch (ParseException e) {
            // this should never happen unless someone is mucking about in the source code
            Log.e(TAG, "Failed to parse date string: " + intent.getStringExtra("date"), e);
            // Throw a RuntimeException because if we use this invalid date data we can potentially
            // corrupt the FeelQueue and state of FeelsBook
            throw new RuntimeException(e);
        }
        Feel.Feelings feeling = Feel.Feelings.valueOf(intent.getStringExtra("feeling"));
        String comment = intent.getStringExtra("comment");
        final int position = intent.getIntExtra("position", 0);

        Feel feel = (Feel) mFeelQueue.toArray()[position];
        mFeelQueue.remove(feel);

        feel.setFeeling(feeling);
        feel.setComment(comment);
        feel.setDate(date);
        mFeelQueue.add(feel);

        mFeelAdapter.notifyDataSetChanged();
        FeelsBookPreferencesManager.saveSharedPreferencesFeelList(getApplicationContext(), mFeelQueue);
    }

    /**
     * Using an onClick binding in the MainActivity so that binding all the feeling buttons is
     * cleaner.
     *
     * @param view {@code View}
     */
    @Override
    public void onClick(View view) {
        Feel.Feelings feeling;
        switch (view.getId()) {
            case R.id.button_anger:
                feeling = Anger;
                break;

            case R.id.button_fear:
                feeling = Fear;
                break;

            case R.id.button_joy:
                feeling = Joy;
                break;

            case R.id.button_love:
                feeling = Love;
                break;

            case R.id.button_sadness:
                feeling = Sadness;
                break;

            case R.id.button_surprise:
                feeling = Surprise;
                break;

            default:
                Log.e(TAG, "Unsupported button pushed: " + view.getId());
                return;
        }
        mFeelQueue.add(new Feel(feeling));
        mFeelAdapter.notifyDataSetChanged();
        FeelsBookPreferencesManager.saveSharedPreferencesFeelList(getApplicationContext(), mFeelQueue);
    }

    /**
     * On Activity start load the FeelQueue from the Android SharedPreferences.
     */
    @Override
    public void onStart() {
        super.onStart();
        mFeelQueue = FeelsBookPreferencesManager.loadSharedPreferencesFeelList(getApplicationContext());
        mFeelAdapter = new FeelAdapter(mFeelQueue);
        mFeelsRecyclerView.setAdapter(mFeelAdapter);
    }
}
