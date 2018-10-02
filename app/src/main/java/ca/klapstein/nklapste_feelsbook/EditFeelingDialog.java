package ca.klapstein.nklapste_feelsbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

public class EditFeelingDialog extends DialogFragment {
    private static final String TAG = "EditFeelingDialog";
    private Spinner feelSpinner;
    private TextView dateEditText;
    private EditText commentEditText;
    private OnSaveButtonClickListener mOnSaveButtonClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnSaveButtonClickListener) {
            mOnSaveButtonClickListener = (OnSaveButtonClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_edit_feel_dialog, null);

        feelSpinner = (Spinner) view.findViewById(R.id.feelSpinner);
        Bundle mArgs = getArguments();
        final String feelingStr = mArgs.getString("feeling");
        if (!feelingStr.equals("")) {
            Feel.Feelings feeling = Feel.Feelings.valueOf(feelingStr);
            setFeelSpinnerDefault(feeling);
        }

        final String date = mArgs.getString("date");
        dateEditText = (TextView) view.findViewById(R.id.dateEditText);
        dateEditText.setText(date);
        dateEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDateEditTextClick(date);
                    }
                }
        );

        final String comment = mArgs.getString("comment");
        commentEditText = (EditText) view.findViewById(R.id.commentEditText);
        commentEditText.setText(comment);

        final int position = mArgs.getInt("position");

        builder.setView(view);
        int title;
        int save;
        if (position >= 0){
            title = R.string.edit_feeling;
            save = R.string.edit;
        } else {
            title = R.string.add_feeling;
            save = R.string.add;
        }
        builder.setTitle(title);
        builder.setPositiveButton(getResources().getString(save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final String comment = commentEditText.getText().toString();
                final Feel.Feelings feeling = Feel.Feelings.valueOf(feelSpinner.getSelectedItem().toString());
                String dateStr = dateEditText.getText().toString();
                Date date;
                try {
                    date = dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    Log.e(TAG, "Failed to parse date string", e);
                    // Throw a RuntimeException because if we use this invalid date data we can potentially
                    // corrupt the FeelTreeSet and state of FeelsBook
                    throw new RuntimeException(e);
                }
                Feel feel = new Feel(feeling, comment, date);
                if (position >= 0) {
                    mOnSaveButtonClickListener.onSaveButtonClick(feel, position);
                } else {
                    mOnSaveButtonClickListener.onSaveButtonClick(feel);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
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
     * Convert a string representation of a feel into the relevant selection number for the
     * EditFeelActivity feelSpinner.
     *
     * @param feeling {@code String}
     */
    public void setFeelSpinnerDefault(Feel.Feelings feeling) {
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
            // Throw a RuntimeException because if we use this invalid date data we can potentially
            // corrupt the FeelTreeSet and state of FeelsBook
            throw new RuntimeException(e);
        }
    }

    public interface OnSaveButtonClickListener {
        void onSaveButtonClick(Feel feel);
        void onSaveButtonClick(Feel feel, int position);
    }
}
