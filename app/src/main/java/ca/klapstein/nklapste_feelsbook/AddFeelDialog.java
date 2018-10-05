package ca.klapstein.nklapste_feelsbook;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Subclass of ModifyFeelDialog to manage adding a new {@code Feel} to FeelsBook.
 *
 * @see ModifyFeelDialog
 */
public class AddFeelDialog extends ModifyFeelDialog {
    public static final String TAG = "AddFeelDialog";

    public static final String FEELING_ARG_TAG = "feeling";

    @Override
    protected CharSequence getDialogTitle() {
        return getResources().getString(R.string.add_feeling);
    }

    @Override
    protected CharSequence getDialogPositiveText() {
        return getResources().getString(R.string.add);
    }

    /**
     * Construct a new {@code Feel} with a default {@code Feeling} provided by the ``feeling``
     * argument.
     *
     * @param args {@code Bundle}
     * @return {@code Feel}
     */
    @Override
    protected Feel getDefaultFeel(@Nullable Bundle args) {
        assert args != null;
        Feeling feeling = Feeling.valueOf(args.getString(FEELING_ARG_TAG));
        return new Feel(feeling);
    }

    /**
     * Return the position of the {@code Feel} within FeelsBook {@code FeelTreeSet}.
     * <p>
     * Since this is adding a new {@code Feel} to FeelsBook it is not within {@code FeelTreeSet}.
     * Thus, return the position as {@code null} to indicate this.
     *
     * @param args {@code Bundle}
     * @return {@code null}
     */
    @Override
    protected Integer getDefaultPosition(@Nullable Bundle args) {
        return null;
    }
}

