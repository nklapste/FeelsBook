package ca.klapstein.nklapste_feelsbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Feel {
    private static final String TAG = "Feel";

    private String comment;
    private Feels feels;
    private String date;

    Feel(Feels feels) {
        this.feels = feels;
        this.comment = "";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
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

    public Feels getFeel() {
        return feels;
    }

    public String getDate() {
        return date;
    }
}
