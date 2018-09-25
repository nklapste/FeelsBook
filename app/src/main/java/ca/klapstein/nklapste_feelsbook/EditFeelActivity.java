package ca.klapstein.nklapste_feelsbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

public class EditFeelActivity extends AppCompatActivity {
    private static final String TAG = "EditFeelActivity";

    private TextView dateEditText;
    private Spinner feelSpinner;
    private EditText commentEditText;

    /**
     * showDateTimePicker is based of a code solution provided by:
     *
     * Abhishek
     * https://stackoverflow.com/users/5242161/abhishek
     * https://stackoverflow.com/questions/2055509/datetime-picker-in-android-application
     *
     * @param date {@code Date} The initial date to start the time pickers at.
     */
    public void showDateTimePicker(Date date) {
        final Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        final Calendar newDate = Calendar.getInstance();
        final Context context = this;
        newDate.setTime(date);
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        newDate.set(Calendar.MINUTE, minute);

                        Log.d(TAG, "Setting new date: " + dateFormat.format(newDate.getTime()));
                        dateEditText.setText(dateFormat.format(newDate.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    // TODO: name refactoring
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_feel);

        feelSpinner = findViewById(R.id.feelSpinner);
        final Feel.Feelings feeling = Feel.Feelings.valueOf(getIntent().getStringExtra("feeling"));
        setFeelSpinnerDefault(feeling);

        dateEditText = findViewById(R.id.dateEditText);
        final String date = getIntent().getStringExtra("date");
        dateEditText.setText(date);
        dateEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDateEditTextClick(date);
                    }
                }
        );

        commentEditText = findViewById(R.id.commentEditText);
        final String comment = getIntent().getStringExtra("comment");
        commentEditText.setText(comment);

        final Button button_save_feel = findViewById(R.id.button_save_feel);
        final int position = getIntent().getIntExtra("position", 0);
        button_save_feel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClick(position);
            }
        });
    }

    /**
     * Convert a string representation of a feel into the relevant selection number for the
     * EditFeelActivity feelSpinner.
     *
     * @param feeling {@code String}
     */
    public void setFeelSpinnerDefault(Feel.Feelings feeling){
        int selection = 0;
        switch (feeling) {
            case Anger:
                selection = 0;
                break;

            case Fear:
                selection = 1;
                break;

            case Joy:
                selection = 2;
                break;

            case Love:
                selection = 3;
                break;

            case Sadness:
                selection = 4;
                break;

            case Surprise:
                selection = 5;
                break;

            default:
                break;
        }
        feelSpinner.setSelection(selection);
    }

    /**
     * On clicking the Date edit text parse the containing text into a {@code Date} and display
     * a date time picker to modify the feel's date.
     *
     * @param date {String}
     */
    public void onDateEditTextClick(String date) {
        try {
            showDateTimePicker(dateFormat.parse(date));
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date string: " + date, e);
        }
    }

    /**
     * On clicking the save button add the modified feel characteristics onto the Intent result
     * and finish the activity.
     *
     * @param position {@code int}
     */
    public void onSaveButtonClick(final int position) {
        final String comment = commentEditText.getText().toString();
        final String feeling = feelSpinner.getSelectedItem().toString();
        String date = dateEditText.getText().toString();
        try {
            date = dateFormat.format(dateFormat.parse(date));
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date string: " + date, e);
            // TODO: error handling
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
}
