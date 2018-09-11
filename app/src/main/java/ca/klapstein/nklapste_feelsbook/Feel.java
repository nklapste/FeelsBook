package ca.klapstein.nklapste_feelsbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Feel {
    private static final String TAG = "Feel";

    public static final String ANGER = "Anger";
    public static final String SADNESS = "Sadness";
    public static final String SURPRISE = "Surprise";
    public static final String JOY = "Joy";
    public static final String FEAR = "Fear";
    public static final String LOVE = "Love";

    private String comment;
    private String feeling;
    private String date;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    Feel(String feeling) {
        this.feeling = feeling;
        this.comment = "";

        try {
            this.date = dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            this.date = null;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
