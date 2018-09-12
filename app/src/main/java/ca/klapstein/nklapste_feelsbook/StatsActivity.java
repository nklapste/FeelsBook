package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

public class StatsActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent intent = getIntent();

        Integer angerTally = intent.getIntExtra("angerTally", 0);
        final TextView angerTextViewNumber = findViewById(R.id.angerTextViewNumber);
        angerTextViewNumber.setText(stringifyTally(angerTally));

        Integer fearTally = intent.getIntExtra("fearTally", 0);
        final TextView fearTextViewNumber = findViewById(R.id.fearTextViewNumber);
        fearTextViewNumber.setText(stringifyTally(fearTally));

        Integer joyTally = intent.getIntExtra("joyTally", 0);
        final TextView joyTextViewNumber = findViewById(R.id.joyTextViewNumber);
        joyTextViewNumber.setText(stringifyTally(joyTally));

        Integer loveTally = intent.getIntExtra("loveTally", 0);
        final TextView loveTextViewNumber = findViewById(R.id.loveTextViewNumber);
        loveTextViewNumber.setText(stringifyTally(loveTally));

        Integer sadnessTally = intent.getIntExtra("sadnessTally", 0);
        final TextView sadnessTextViewNumber = findViewById(R.id.sadnessTextViewNumber);
        sadnessTextViewNumber.setText(stringifyTally(sadnessTally));

        Integer surpriseTally = intent.getIntExtra("surpriseTally", 0);
        final TextView surpriseTextViewNumber = findViewById(R.id.surpriseTextViewNumber);
        surpriseTextViewNumber.setText(stringifyTally(surpriseTally));
    }
}
