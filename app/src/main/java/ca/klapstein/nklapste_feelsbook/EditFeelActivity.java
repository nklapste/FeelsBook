package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

public class EditFeelActivity extends AppCompatActivity {
    private static final String TAG = "EditFeelActivity";

    // TODO: name refactoring
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_feel);

        final Spinner feelSpinner = (Spinner) findViewById(R.id.feelSpinner);
        final String feeling = getIntent().getStringExtra("feeling");
        int selection = 0;
        switch (feeling) {
            case Feel.ANGER:
                selection = 0;
                break;

            case Feel.FEAR:
                selection = 1;
                break;

            case Feel.JOY:
                selection = 2;
                break;

            case Feel.LOVE:
                selection = 3;
                break;

            case Feel.SADNESS:
                selection = 4;
                break;

            case Feel.SURPRISE:
                selection = 5;
                break;

            default:
                break;
        }
        feelSpinner.setSelection(selection);

        final EditText dateEditText = (EditText) findViewById(R.id.dateEditText);
        final String date = getIntent().getStringExtra("date");
        dateEditText.setText(date);
        // TODO: validate date string

        final EditText commentEditText = (EditText) findViewById(R.id.commentEditText);
        final String comment = getIntent().getStringExtra("comment");
        commentEditText.setText(comment);

        final int position = getIntent().getIntExtra("position", 0);

        final Button button_save_feel = (Button) findViewById(R.id.button_save_feel);
        button_save_feel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String comment = commentEditText.getText().toString();
                final String feeling = feelSpinner.getSelectedItem().toString();
                String date = dateEditText.getText().toString();
                try {
                    Log.d(TAG, "Parsing date string: " + date);
                    date = dateFormat.format(dateFormat.parse(date));
                } catch (ParseException e) {
                    Log.e(TAG, "Failed to parse date string: " + date, e);
                    Toast.makeText(getApplicationContext(), "Inputted date is incorrect! Please conform to a yyyy-MM-ddTHH:mm:ss Datetime format.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra("date", date);
                data.putExtra("feeling", feeling);
                data.putExtra("comment", comment);
                data.putExtra("position", position);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
