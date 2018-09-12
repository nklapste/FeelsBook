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
    private Context context;
    private TextView dateEditText;

    /**
     * Abhishek
     * https://stackoverflow.com/users/5242161/abhishek
     * https://stackoverflow.com/questions/2055509/datetime-picker-in-android-application
     *
     * @param date {@code Date}
     */
    public void showDateTimePicker(Date date) {
        final Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        final Calendar newDate = Calendar.getInstance();
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
        context = this;

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

        dateEditText = (TextView) findViewById(R.id.dateEditText);
        final String date = getIntent().getStringExtra("date");
        dateEditText.setText(date);
        dateEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            showDateTimePicker(dateFormat.parse(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

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
        });
    }
}
