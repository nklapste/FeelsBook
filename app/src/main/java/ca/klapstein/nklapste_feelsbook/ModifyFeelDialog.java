package ca.klapstein.nklapste_feelsbook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.ikovac.timepickerwithseconds.MyTimePickerDialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;


/**
 * Abstract class defining the boilerplate for adding/editing a {@code Feel} within FeelsBook.
 */
abstract class ModifyFeelDialog extends DialogFragment {
    private static final String TAG = "ModifyFeelDialog";

    private Spinner feelSpinner;
    private TextView dateEditText;
    private EditText commentEditText;
    private OnPositiveButtonClickListener mOnPositiveButtonClickListener;
    private Feel feel;

    @Nullable
    private Integer position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPositiveButtonClickListener) {
            mOnPositiveButtonClickListener = (OnPositiveButtonClickListener) context;
        }
    }

    /**
     * Get the Title to be set in {@code onCreateDialog}'s {@code AlertDialog.Builder}.
     *
     * @return {@code CharSequence}
     */
    protected abstract CharSequence getDialogTitle();

    /**
     * Get the positive text to be set in {@code onCreateDialog}'s {@code AlertDialog.Builder}.
     *
     * @return {@code CharSequence}
     */
    protected abstract CharSequence getDialogPositiveText();

    /**
     * Get the negative text to be set in {@code onCreateDialog}'s {@code AlertDialog.Builder}.
     *
     * @return {@code CharSequence}
     */
    private CharSequence getDialogNegativeText() {
        return getResources().getString(R.string.cancel);
    }

    /**
     * Obtain or construct the {@code Feel} to be modified within this dialog.
     *
     * @param args {@code Bundle}
     * @return {@code Feel} to be modified within this dialog.
     */
    protected abstract Feel getDefaultFeel(@Nullable Bundle args);

    /**
     * Return the position of the Feel within FeelsBook {@code FeelTreeSet}.
     *
     * @param args {@code Bundle}
     * @return {@code Integer} the position of the Feel within FeelsBook {@code FeelTreeSet}.
     */
    protected abstract Integer getDefaultPosition(@Nullable Bundle args);

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_edit_feel_dialog, null);

        // get the default input Views
        dateEditText = (TextView) view.findViewById(R.id.dateEditText);
        feelSpinner = (Spinner) view.findViewById(R.id.feelSpinner);
        commentEditText = (EditText) view.findViewById(R.id.commentEditText);

        dateEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDateEditTextClick();
                    }
                }
        );


        // programmatically set the contents of the feelSpinner from all values of Feel.Feeling
        feelSpinner.setAdapter(new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Feeling.values()));

        feel = getDefaultFeel(getArguments());
        position = getDefaultPosition(getArguments());

        dateEditText.setText(dateFormat.format(feel.getDate()));
        feelSpinner.setSelection(feel.getFeeling().ordinal());
        commentEditText.setText(feel.getComment());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getDialogTitle());
        builder.setPositiveButton(getDialogPositiveText(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    feel.setDate(dateFormat.parse(dateEditText.getText().toString()));
                } catch (ParseException e) {
                    Log.e(TAG, "Failed to parse date string", e);
                    // Throw a RuntimeException because if we use this invalid date data we can potentially
                    // corrupt the FeelTreeSet and state of FeelsBook
                    throw new RuntimeException(e);
                }
                feel.setFeeling(Feeling.valueOf(feelSpinner.getSelectedItem().toString()));
                feel.setComment(commentEditText.getText().toString());
                if (position != null) {
                    mOnPositiveButtonClickListener.onEditButtonClick(feel, position);
                } else {
                    mOnPositiveButtonClickListener.onAddButtonClick(feel);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getDialogNegativeText(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * showDateTimePicker is based of a code solution provided by:
     * <p>
     * Abhishek:
     * https://stackoverflow.com/users/5242161/abhishek
     * https://stackoverflow.com/questions/2055509/datetime-picker-in-android-application
     * <p>
     * Uses TimePickerWithSeconds by IvanKovac:
     * https://github.com/IvanKovac/TimePickerWithSeconds
     *
     * @param date    {@code Date} The initial date to start the time pickers at.
     * @param context {@code Context}
     */
    private void showDateTimePicker(Date date, final Context context) {
        final Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        final Calendar newDate = Calendar.getInstance();
        newDate.setTime(date);
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                new MyTimePickerDialog(context, new MyTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                        newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, seconds);
                        Log.d(TAG, "Setting new date: " + dateFormat.format(newDate.getTime()));
                        dateEditText.setText(dateFormat.format(newDate.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), currentDate.get(Calendar.SECOND), true).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * On clicking the dateEditText parse the containing text into a {@code Date} and display
     * a date time picker to modify the feel's date.
     *
     * @throws RuntimeException if the date contained in {@code dateEditText} fails to be parsed
     *                          into {@code dateFormat}.
     */
    private void onDateEditTextClick() {
        String date = dateEditText.getText().toString();
        try {
            showDateTimePicker(dateFormat.parse(date), getContext());
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date string: " + date, e);
            // Throw a RuntimeException because if we use this invalid date data we can potentially
            // corrupt the FeelTreeSet and state of FeelsBook
            throw new RuntimeException(e);
        }
    }

    /**
     * Interface to provide inter-Fragment communication.
     * <p>
     * This is implemented in MainActivity to provide inter-Fragment communication between
     * AddFeelDialog and/or EditFeelDialog to FeelTab.
     */
    public interface OnPositiveButtonClickListener {
        void onAddButtonClick(Feel feel);

        void onEditButtonClick(Feel feel, int position);
    }
}
