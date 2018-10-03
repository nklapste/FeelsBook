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
     * Compare to {@code Feel}s.
     * <p>
     * Only compare them by their date. Having a later date be considered larger.
     * This compareTo method is utilised for the automatic sorting of {@code FeelTreeSet}.
     *
     * @param feel {@code Feel}
     * @return {@code int}
     */
    @Override
    public int compareTo(@NonNull Feel feel) {
        int dateComparison = -this.getDate().compareTo(feel.getDate());
        if (dateComparison == 0) {
            return this.getFeeling().compareTo(feel.getFeeling());
        } else {
            return dateComparison;
        }
    }

}
