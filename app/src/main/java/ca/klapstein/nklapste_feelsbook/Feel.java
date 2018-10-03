package ca.klapstein.nklapste_feelsbook;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Object Class defining a feeling as described by:
 * <p>
 * http://changingminds.org/explanations/emotions/basic%20emotions.htm from
 * Shaver, P., Schwartz, J., Kirson, D., & O'Connor, C. (2001).
 * Emotional Knowledge: Further Exploration of a Prototype Approach.
 * In G. Parrott (Eds.), Emotions in Social Psychology: Essential Readings (pp. 26-56).
 * Philadelphia, PA: Psychology Press.
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
        if (dateComparison==0){
            return this.getFeeling().compareTo(feel.getFeeling());
        } else {
            return dateComparison;
        }
    }

    /**
     * Notes on implementation:
     *
     * Instead of using subclassing I define all supported feels by this enumerator.
     * Thus, all string values that are permitted to be a ``feeling`` are controlled.
     *
     * This also allows me to generically create other resources within FeelsBook such as the
     * stats page, tallies of all feelings, and add feeling buttons. This could be done with
     * subclassing, but, it could get quickly messy onto how to define the domain of all valid
     * feelings.
     *
     * If I add another Feeling enumeration value the rest of FeelsBook should properly accommodate
     * it.
     *
     * The only issue with this implementation is that adding additional ``feeling`` specific data
     * other than the string name value of the ``feeling`` is difficult. However, since this
     * app doesn't require such components I didn't figure this extensibility was a priority.
     */
    public enum Feeling {
        Anger,
        Fear,
        Joy,
        Love,
        Sadness,
        Surprise,
    }
}
