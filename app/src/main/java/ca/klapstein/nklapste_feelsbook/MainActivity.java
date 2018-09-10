package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int EDIT_COMMENT_REQUEST_CODE = 1;

    private FeelAdapter mAdapter;

    private ArrayList<Feel> feelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO load old feels list
        feelList = new ArrayList<>();

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
                Feel feel = feelList.get(position);
                Toast.makeText(getApplicationContext(), feel.getFeel()+ " is selected!", Toast.LENGTH_SHORT).show();
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
            String comment = intent.getStringExtra("comment");
            int position = intent.getIntExtra("position", 0);
            Feel feel = feelList.get(position);
            feel.setComment(comment);
            feelList.set(position, feel);
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        Feels feels = null;
        switch (v.getId()){
            case R.id.button_love:
                feels = Feels.LOVE;
                break;

            case R.id.button_anger:
                feels = Feels.ANGER;
                break;

            case R.id.button_joy:
                feels = Feels.JOY;
                break;

            case R.id.button_sadness:
                feels = Feels.SADNESS;
                break;

            case R.id.button_surprise:
                feels = Feels.SURPRISE;
                break;

            case R.id.button_fear:
                feels = Feels.FEAR;
                break;

            default:
                break;
        }
        feelList.add(new Feel(feels));
        mAdapter.notifyDataSetChanged();
    }
}
