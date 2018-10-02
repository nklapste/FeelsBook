package ca.klapstein.nklapste_feelsbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;

public class StatsTab extends Fragment {
    private static final String TAG = "StatsTab";

    private TableLayout stats_table;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stats_tab, container, false);
    }

    /**
     * Convert a feeling tally {@code int} into a string representation.
     * <p>
     * Also consider the {@code Locale} of the device when formatting.
     *
     * @param tally {@code int} The int representing to total tally of a feeling type.
     * @return {@code String} The Locale specific string representation of tally.
     */
    private String stringifyTally(int tally) {
        return String.format(Locale.getDefault(), "%d", tally);
    }

    /**
     * If the stats Fragment is visible on the screen redraw the feelings stats table and
     * repopulate it with the most updated tally of all feelings.
     *
     * @param isVisibleToUser {@code boolean}
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // get the current mFeelTreeSet from Android's SharedPreferences
            FeelTreeSet mFeelTreeSet = FeelsBookPreferencesManager.loadSharedPreferencesFeelList(getContext().getApplicationContext());

            // dynamically recreate and populate the table for all Feeling tallies.
            int table_index = 0;
            stats_table.removeAllViews();
            for (Feel.Feeling feel : Feel.Feeling.values()) {
                TableRow row = new TableRow(getContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                row.setPadding(15,15,15,0);
                TextView title = (TextView) new TextView(getContext());
                title.setText(String.format("%s:", feel.toString()));
                title.setPadding(0,0,15,0);
                row.addView(title);
                TextView tally = (TextView) new TextView(getContext());
                tally.setText(stringifyTally(mFeelTreeSet.getFeelingTallies().get(feel)));
                row.addView(tally);
                stats_table.addView(row, table_index);
                table_index++;
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stats_table = (TableLayout) view.findViewById(R.id.stats_table);
    }
}
