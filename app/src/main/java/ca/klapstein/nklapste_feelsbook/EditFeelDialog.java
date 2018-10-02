package ca.klapstein.nklapste_feelsbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;

/**
 * Subclass of ModifyFeelDialog to manage editing an existing Feel within FeelsBook.
 */
public class EditFeelDialog extends ModifyFeelDialog {
    public static final String TAG = "EditFeelDialog";

    @Override
    AlertDialog buildDialog(View view) {
        // grab the arguments for Editing a Feel
        // get the Feel's date, feeling and comment
        final Bundle mArgs = getArguments();
        final String date = mArgs.getString("date");
        Feel.Feeling feeling = Feel.Feeling.valueOf(mArgs.getString("feeling"));
        final String comment = mArgs.getString("comment");

        dateEditText.setText(date);
        // Set the feelSpinners default selection to the original feel's feel
        feelSpinner.setSelection(feeling.ordinal());
        commentEditText.setText(comment);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.edit_feeling);
        builder.setPositiveButton(getResources().getString(R.string.edit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final String comment = commentEditText.getText().toString();
                final Feel.Feeling feeling = Feel.Feeling.valueOf(feelSpinner.getSelectedItem().toString());
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
                mOnSaveButtonClickListener.onSaveButtonClick(feel, mArgs.getInt("position"));
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
}
