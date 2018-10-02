package ca.klapstein.nklapste_feelsbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class StatsTab extends Fragment {
    private static final String TAG = "StatsTab";

    private TextView angerTextViewNumber;
    private TextView fearTextViewNumber;
    private TextView joyTextViewNumber;
    private TextView loveTextViewNumber;
    private TextView sadnessTextViewNumber;
    private TextView surpriseTextViewNumber;

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FeelQueue mFeelQueue = FeelsBookPreferencesManager.loadSharedPreferencesFeelList(getContext().getApplicationContext());
            angerTextViewNumber.setText(stringifyTally(mFeelQueue.getAngerTally()));
            fearTextViewNumber.setText(stringifyTally(mFeelQueue.getFearTally()));
            joyTextViewNumber.setText(stringifyTally(mFeelQueue.getJoyTally()));
            loveTextViewNumber.setText(stringifyTally(mFeelQueue.getLoveTally()));
            sadnessTextViewNumber.setText(stringifyTally(mFeelQueue.getSadnessTally()));
            surpriseTextViewNumber.setText(stringifyTally(mFeelQueue.getSurpriseTally()));
        } else {
            Log.d(TAG, "Fragment is not visible.");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surpriseTextViewNumber = (TextView) view.findViewById(R.id.surpriseTextViewNumber);
        sadnessTextViewNumber = (TextView) view.findViewById(R.id.sadnessTextViewNumber);
        loveTextViewNumber = (TextView) view.findViewById(R.id.loveTextViewNumber);
        joyTextViewNumber = (TextView) view.findViewById(R.id.joyTextViewNumber);
        fearTextViewNumber = (TextView) view.findViewById(R.id.fearTextViewNumber);
        angerTextViewNumber = (TextView) view.findViewById(R.id.angerTextViewNumber);
    }
}
