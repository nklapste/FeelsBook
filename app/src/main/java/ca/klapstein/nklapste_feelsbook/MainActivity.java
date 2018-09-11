package ca.klapstein.nklapste_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int EDIT_COMMENT_REQUEST_CODE = 1;

    private FeelAdapter mAdapter;

    private ArrayList<Feel> feelList = new ArrayList<>();

    public static void saveSharedPreferencesFeelList(Context context, ArrayList<Feel> feelList) {
        SharedPreferences mPrefs = context.getSharedPreferences("feelList", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feelList);
        prefsEditor.putString("myJson", json);
        prefsEditor.apply();
    }

    public static ArrayList<Feel> loadSharedPreferencesFeelList(Context context) {
        ArrayList<Feel> feelList = new ArrayList<Feel>();
        SharedPreferences mPrefs = context.getSharedPreferences("feelList", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            feelList = new ArrayList<Feel>();
        } else {
            Type type = new TypeToken<ArrayList<Feel>>() {
            }.getType();
            feelList = gson.fromJson(json, type);
        }
        return feelList;
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferencesFeelList(getApplicationContext(), feelList);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO save feelList on destruction and load old feels list
        feelList = loadSharedPreferencesFeelList(getApplicationContext());

        setContentView(R.layout.listview_layout);
        RecyclerView mFeelsRecyclerView = (RecyclerView) findViewById(R.id.feels_recycler_view);

        mFeelsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFeelsRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FeelAdapter(feelList);
        mFeelsRecyclerView.setAdapter(mAdapter);

        mFeelsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mFeelsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

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
                                feelList.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                mAdapter.notifyItemRangeChanged(position, feelList.size());
                                return true;
                            case R.id.button_comment:
                                Intent intent = new Intent(getApplicationContext(), EditFeel.class);
                                intent.putExtra("date", feelList.get(position).getDate());
                                intent.putExtra("feeling", feelList.get(position).getFeeling());
                                intent.putExtra("comment", feelList.get(position).getComment());
                                intent.putExtra("position", position);
                                startActivityForResult(intent, 1);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EDIT_COMMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            String date = intent.getStringExtra("date");
            String feeling = intent.getStringExtra("feeling");
            String comment = intent.getStringExtra("comment");
            int position = intent.getIntExtra("position", 0);

            Feel feel = feelList.get(position);
            feel.setComment(comment);
            feel.setDate(date);
            feel.setFeeling(feeling);

            feelList.set(position, feel);

            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        String feels = null;
        switch (v.getId()){
            case R.id.button_anger:
                feels = Feel.ANGER;
                break;

            case R.id.button_fear:
                feels = Feel.FEAR;
                break;

            case R.id.button_joy:
                feels = Feel.JOY;
                break;

            case R.id.button_love:
                feels = Feel.LOVE;
                break;

            case R.id.button_sadness:
                feels = Feel.SADNESS;
                break;

            case R.id.button_surprise:
                feels = Feel.SURPRISE;
                break;

            default:
                break;
        }
        feelList.add(0, new Feel(feels));
        mAdapter.notifyDataSetChanged();

        saveSharedPreferencesFeelList(getApplicationContext(), feelList);
    }
}
