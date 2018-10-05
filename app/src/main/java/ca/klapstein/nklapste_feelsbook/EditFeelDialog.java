package ca.klapstein.nklapste_feelsbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.util.Date;

import static ca.klapstein.nklapste_feelsbook.Feel.dateFormat;


/**
 * Subclass of ModifyFeelDialog to manage editing an existing {@code Feel} within FeelsBook.
 *
 * @see ModifyFeelDialog
 */
public class EditFeelDialog extends ModifyFeelDialog {
    public static final String TAG = "EditFeelDialog";

    public static final String FEELING_ARG_TAG = "feeling";
    public static final String DATE_ARG_TAG = "date";
    public static final String COMMENT_ARG_TAG = "comment";
    public static final String POSITION_ARG_TAG = "position";

    @Override
    protected CharSequence getDialogTitle() {
        return getResources().getString(R.string.edit_feeling);
    }

    @Override
    protected CharSequence getDialogPositiveText() {
        return getResources().getString(R.string.edit);
    }

    /**
     * Reconstruct the {@code Feel} that is being modified within the FeelsBook {@code FeelTreeSet}.
     *
     * @param args {@code Bundle}
     * @return {@code Feel} to be modified within this dialog.
     * @throws RuntimeException if the date string from the date argument fails to be parsed into
     *                          the {@code dateFormat}.
     */
    @Override
    protected Feel getDefaultFeel(@Nullable Bundle args) {
        assert args != null;
        Feeling feeling = Feeling.valueOf(args.getString(FEELING_ARG_TAG));
        Date date;
        try {
            date = dateFormat.parse(args.getString(DATE_ARG_TAG));
        } catch (ParseException e) {
            Log.e(TAG, "Failed to parse date string", e);
            // Throw a RuntimeException because if we use this invalid date data we can potentially
            // corrupt the FeelTreeSet and state of FeelsBook
            throw new RuntimeException(e);
        }
        String comment = args.getString(COMMENT_ARG_TAG);
        return new Feel(feeling, comment, date);
    }

    /**
     * Return the position of the {@code Feel} being modified within the FeelsBook {@code FeelTreeSet}.
     * <p>
     * Since this is editing an existing {@code Feel} within FeelsBook it **must** have a position
     * within FeelsBook's {@code FeelTreeSet}.
     *
     * @param args {@code Bundle}
     * @return {@code Integer} the position of the Feel within FeelsBook {@code FeelTreeSet}.
     */
    @Override
    protected Integer getDefaultPosition(@Nullable Bundle args) {
        assert args != null;
        return args.getInt(POSITION_ARG_TAG);
    }
}
