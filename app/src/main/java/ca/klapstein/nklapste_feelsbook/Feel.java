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
public class Feel implements Comparable<Feel>{
   private static final String TAG = "Feel";

   public enum Feelings {
        Anger,
        Sadness,
        Surprise,
        Joy,
        Fear,
        Love,
    }

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    private String comment;
    private Feelings feeling;
    private Date date;

    Feel(Feelings feeling, String comment, Date date) {
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

    public Feelings getFeeling() {
        return feeling;
    }

    public void setFeeling(Feelings feeling) {
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
     *
     * Only compare them by their date. Having a later date be considered larger.
     * This compareTo method is utilised for the automatic sorting of {@code FeelQueue}.
     *
     * @param feel {@code Feel}
     * @return {@code int}
     */
    @Override
    public int compareTo(@NonNull Feel feel) {
        return -this.getDate().compareTo(feel.getDate());
    }
}
