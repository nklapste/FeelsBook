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
 * Subclass of ModifyFeelDialog to manage adding a new Feel to FeelsBook.
 */
public class AddFeelDialog extends ModifyFeelDialog {
    public static final String TAG = "AddFeelDialog";

    @Override
    AlertDialog buildDialog(View view) {
        dateEditText.setText(dateFormat.format(new Date()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Bundle mArgs = getArguments();
        Feel.Feeling feeling = Feel.Feeling.valueOf(mArgs.getString("feeling"));
        // Set the feelSpinners default selection to the original feel's feel
        feelSpinner.setSelection(feeling.ordinal());

        builder.setView(view);
        builder.setTitle(R.string.add_feeling);
        builder.setPositiveButton(getResources().getString(R.string.add), new DialogInterface.OnClickListener() {
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
                mOnSaveButtonClickListener.onSaveButtonClick(feel);
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

