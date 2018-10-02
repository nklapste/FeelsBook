package ca.klapstein.nklapste_feelsbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

/**
 * Abstract class defining the boilerplate for adding/editing a Feel within FeelsBook.
 */
abstract public class ModifyFeelDialog extends DialogFragment {
    private static final String TAG = "ModifyFeelDialog";

    protected Spinner feelSpinner;
    protected TextView dateEditText;
    protected EditText commentEditText;
    protected OnSaveButtonClickListener mOnSaveButtonClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnSaveButtonClickListener) {
            mOnSaveButtonClickListener = (OnSaveButtonClickListener) context;
        }
    }

    /**
     * Build the AlertDialog for the specific Feel modification.
     *
     * @param view {@code View}
     * @return {@code AlertDialog}
     */
    abstract AlertDialog buildDialog(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_edit_feel_dialog, null);

        // get the default input Views
        dateEditText = (TextView) view.findViewById(R.id.dateEditText);
        dateEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDateEditTextClick();
                    }
                }
        );
        commentEditText = (EditText) view.findViewById(R.id.commentEditText);
        feelSpinner = (Spinner) view.findViewById(R.id.feelSpinner);

        // programmatically set the contents of the feelSpinner from all values of Feel.Feeling
        feelSpinner.setAdapter(new ArrayAdapter<Feel.Feeling>(getContext().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Feel.Feeling.values()));

        return buildDialog(view);
    }

    /**
     * showDateTimePicker is based of a code solution provided by:
     * <p>
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
        final Context context = getContext();
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

    /**
     * On clicking the Date edit text parse the containing text into a {@code Date} and display
     * a date time picker to modify the feel's date.
     */
    public void onDateEditTextClick() {
        String date = dateEditText.getText().toString();
        try {
            showDateTimePicker(dateFormat.parse(date));
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date string: " + date, e);
            // Throw a RuntimeException because if we use this invalid date data we can potentially
            // corrupt the FeelTreeSet and state of FeelsBook
            throw new RuntimeException(e);
        }
    }

    /**
     * Interface to provide inter-Fragment communication.
     *
     * This is implemented in MainActivity to provide inter-Fragment communication between
     * AddFeelDialog and/or EditFeelDialog to FeelingsTab.
     */
    public interface OnSaveButtonClickListener {
        void onSaveButtonClick(Feel feel);

        void onSaveButtonClick(Feel feel, int position);
    }
}
