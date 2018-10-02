package ca.klapstein.nklapste_feelsbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

public class FeelingsTab extends Fragment {
    private static final String TAG = "FeelingsTab";

    private FeelAdapter mFeelAdapter;
    private FeelTreeSet mFeelTreeSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feelings_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mFeelTreeSet = FeelsBookPreferencesManager.loadSharedPreferencesFeelList(getContext().getApplicationContext());
        mFeelAdapter = new FeelAdapter(mFeelTreeSet);

        // define the RecyclerView listing Feels
        RecyclerView mFeelsRecyclerView = view.findViewById(R.id.feels_recycler_view);
        mFeelsRecyclerView.setHasFixedSize(true);
        mFeelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFeelsRecyclerView.setAdapter(mFeelAdapter);
        // set the onClick and onLongClick functions for the RecycleView
        mFeelsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mFeelsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                onFeelListItemClick(view, position);
            }
        }));

        // Define the FloatingActionButton for adding new feels
        final FloatingActionButton add_feeling_button = (FloatingActionButton) view.findViewById(R.id.addFeelingButton);
        add_feeling_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // create and show the AddFeelDialog
                AddFeelDialog addFeelDialog = new AddFeelDialog();
                Bundle args = new Bundle();
                addFeelDialog.setArguments(args);
                addFeelDialog.show(ft, AddFeelDialog.TAG);
            }
        });
    }

    /**
     * Create a popup menu on a long click of a Feel.
     * <p>
     * This menu provides two options:
     * 1. Edit the Feel.
     * 2. Delete the Feel.
     *
     * @param view     {@code View}
     * @param position {@code int}
     */
    private void onFeelListItemClick(View view, final int position) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(getContext(), view);

        final Feel feel = (Feel) mFeelTreeSet.toArray()[position];
        //inflating menu from xml resource
        popup.inflate(R.menu.feel_options_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.button_delete:
                        deleteFeel(feel);
                        return true;
                    case R.id.button_edit_feeling:
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }
                        ft.addToBackStack(null);

                        // create and show the EditFeelDialog
                        EditFeelDialog editFeelDialog = new EditFeelDialog();
                        Bundle args = new Bundle();
                        // editFeelDialog requires some extra arguments noting the Feel to be edited
                        args.putInt("position", position);
                        args.putString("feeling", feel.getFeeling().toString());
                        args.putString("comment", feel.getComment());
                        args.putString("date", dateFormat.format(feel.getDate()));
                        editFeelDialog.setArguments(args);
                        editFeelDialog.show(ft, EditFeelDialog.TAG);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    /**
     * Delete a feel from the mFeelTreeSet
     *
     * @param feel {@code Feel}
     */
    void deleteFeel(Feel feel) {
        mFeelTreeSet.remove(feel);
        mFeelAdapter.notifyDataSetChanged();
        FeelsBookPreferencesManager.saveSharedPreferencesFeelList(getContext().getApplicationContext(), mFeelTreeSet);
    }

    /**
     * Add a feel into the mFeelTreeSet.
     *
     * @param feel {@code Feel}
     */
    void addFeel(Feel feel) {
        mFeelTreeSet.add(feel);
        mFeelAdapter.notifyDataSetChanged();
        FeelsBookPreferencesManager.saveSharedPreferencesFeelList(getContext().getApplicationContext(), mFeelTreeSet);
    }

    /**
     * Edit a feel within the mFeelTreeSet.
     * <p>
     * Remove the original feel from the FeelTreeSet and replace it with the new feel.
     *
     * @param newFeel  {@code Feel}
     * @param position {@code int}
     */
    void editFeel(Feel newFeel, final int position) {
        Feel oldFeel = (Feel) mFeelTreeSet.toArray()[position];
        mFeelTreeSet.remove(oldFeel);
        mFeelTreeSet.add(newFeel);
        mFeelAdapter.notifyDataSetChanged();
        FeelsBookPreferencesManager.saveSharedPreferencesFeelList(getContext().getApplicationContext(), mFeelTreeSet);
    }
}
