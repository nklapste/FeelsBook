package ca.klapstein.nklapste_feelsbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeelCard {
    private static final String TAG = "FeelCard";

    private String comment;
    private Feel feel;
    private String date;

    FeelCard(Feel feel) {
        this.feel = feel;
        this.comment = "";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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

    public Feel getFeel() {
        return feel;
    }

    public String getDate() {
        return date;
    }
}
