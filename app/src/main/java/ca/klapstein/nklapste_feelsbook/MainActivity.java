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

    private MyAdapter mAdapter;

    private ArrayList<FeelCard> feels_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO load old feels list
        feels_list = new ArrayList<>();

        setContentView(R.layout.listview_layout);
        RecyclerView mFeelsRecyclerView = (RecyclerView) findViewById(R.id.feels_recycler_view);

        mFeelsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mFeelsRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(feels_list);
        mFeelsRecyclerView.setAdapter(mAdapter);

        mFeelsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mFeelsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FeelCard feelCard = feels_list.get(position);
                Toast.makeText(getApplicationContext(), feelCard.getFeel()+ " is selected!", Toast.LENGTH_SHORT).show();
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
                                feels_list.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                mAdapter.notifyItemRangeChanged(position, feels_list.size());
                                return true;
                            case R.id.button_comment:
                                Intent intent = new Intent(getApplicationContext(), EditFeel.class);
                                intent.putExtra("comment", feels_list.get(position).getComment());
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
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intent) {
        if (requestCode == EDIT_COMMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            String comment = intent.getStringExtra("comment");
            int position = intent.getIntExtra("position", 0);
            Log.d(TAG, "setting comment: "+comment);
            FeelCard feelCard = feels_list.get(position);
            feelCard.setComment(comment);
            feels_list.set(position, feelCard);
            mAdapter.notifyItemChanged(position);
//            mAdapter.notifyItemRangeChanged(position, feels_list.size());

        }
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

            case R.id.button_fear:
                feel = Feel.FEAR;
                break;

            default:
                break;
        }
        feels_list.add(new FeelCard(feel));
        mAdapter.notifyDataSetChanged();
    }
}
