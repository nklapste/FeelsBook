package ca.klapstein.nklapste_feelsbook;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Object Class defining a Feel. It contains a {@code Date} and {@code Feeling} and can
 * potentially contain an additional {@code String} comment.
 */
public class Feel implements Comparable<Feel> {
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    private static final String TAG = "Feel";
    private String comment;
    private Feeling feeling;
    private Date date;

    /**
     * Construct a {@code Feel} an effective container for {@code Feel}.
     *
     * @param feeling {@code Feeling} the feeling felt.
     */
    Feel(Feeling feeling) {
        this.feeling = feeling;
        this.comment = "";
        this.date = new Date();
    }

    /**
     * Construct a {@code Feel} an effective container for {@code Feel}.
     *
     * @param feeling {@code Feeling} the feeling felt.
     * @param date {@code} the date the feel was felt.
     */
    Feel(Feeling feeling, Date date) {
        this.feeling = feeling;
        this.comment = "";
        this.date = date;
    }

    /**
     * Construct a {@code Feel} an effective container for {@code Feel}.
     *
     * @param feeling {@code Feeling} the feeling felt.
     * @param comment {@code String} an optional comment to be added to the feeling.
     * @param date {@code} the date the feel was felt.
     */
    Feel(Feeling feeling, String comment, Date date) {
        this.feeling = feeling;
        this.comment = comment;
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public void setFeeling(Feeling feeling) {
        this.feeling = feeling;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Compare two {@code Feel}s.
     * <p>
     * Compare them by their date, feeling, then comment in that priority.
     * A later date is considered larger.
     * <p>
     * This compareTo method is utilised for the automatic sorting of {@code FeelTreeSet}.
     *
     * @param feel {@code Feel}
     * @return {@code int}
     */
    @Override
    public int compareTo(@NonNull Feel feel) {
        int dateComparison = -this.getDate().compareTo(feel.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        }

        int feelComparison = this.getFeeling().compareTo(feel.getFeeling());
        if (feelComparison != 0) {
            return feelComparison;
        }

        return this.getComment().compareTo(feel.getComment());
    }
}
