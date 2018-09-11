package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Intent intent = getIntent();

        Integer angerTally = intent.getIntExtra("angerTally", 0);
        final TextView angerTextViewNumber = findViewById(R.id.angerTextViewNumber);
        angerTextViewNumber.setText(angerTally.toString());

        Integer fearTally = intent.getIntExtra("fearTally", 0);
        final TextView fearTextViewNumber = findViewById(R.id.fearTextViewNumber);
        fearTextViewNumber.setText(fearTally.toString());

        Integer joyTally = intent.getIntExtra("joyTally", 0);
        final TextView joyTextViewNumber = findViewById(R.id.joyTextViewNumber);
        joyTextViewNumber.setText(joyTally.toString());

        Integer loveTally = intent.getIntExtra("loveTally", 0);
        final TextView loveTextViewNumber = findViewById(R.id.loveTextViewNumber);
        loveTextViewNumber.setText(loveTally.toString());

        Integer sadnessTally = intent.getIntExtra("sadnessTally", 0);
        final TextView sadnessTextViewNumber = findViewById(R.id.sadnessTextViewNumber);
        sadnessTextViewNumber.setText(sadnessTally.toString());

        Integer surpriseTally = intent.getIntExtra("surpriseTally", 0);
        final TextView surpriseTextViewNumber = findViewById(R.id.surpriseTextViewNumber);
        surpriseTextViewNumber.setText(surpriseTally.toString());
    }

}
