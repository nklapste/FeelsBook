package ca.klapstein.nklapste_feelsbook;

public class FeelCard {
    private static final String TAG = "FeelCard";

    private String comment;
    private Feel feel;

    FeelCard(Feel feel) {
        this.feel = feel;
        this.comment = "";
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
}
