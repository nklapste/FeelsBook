package ca.klapstein.nklapste_feelsbook;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeelList extends ArrayList<Feel> {
    private static final String TAG = "FeelList";

    private Integer angerTally;
    private Integer fearTally;
    private Integer joyTally;
    private Integer loveTally;
    private Integer sadnessTally;
    private Integer surpriseTally;

    FeelList() {
        this.angerTally = 0;
        this.fearTally = 0;
        this.angerTally = 0;
        this.joyTally = 0;
        this.loveTally = 0;
        this.sadnessTally = 0;
        this.surpriseTally = 0;
    }

    private Comparator<Feel> feelDateComparator = new Comparator<Feel>() {
        @Override
        public int compare(Feel o1, Feel o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };

    @Override
    public Feel remove(int index) {
        String feeling = this.get(index).getFeeling();

        switch (feeling) {
            case Feel.ANGER:
                angerTally -= 1;
                break;

            case Feel.FEAR:
                fearTally -= 1;
                break;

            case Feel.JOY:
                joyTally -= 1;
                break;

            case Feel.LOVE:
                loveTally -= 1;
                break;

            case Feel.SADNESS:
                sadnessTally -= 1;
                break;

            case Feel.SURPRISE:
                surpriseTally -= 1;
                break;

            default:
                Log.e(TAG, "Unsupported feeling attempted to be tallied: " + feeling);
                break;
        }
        return super.remove(index);
    }

    @Override
    public boolean add(Feel feel) {
        String feeling = feel.getFeeling();
        switch (feeling) {
            case Feel.ANGER:
                angerTally += 1;
                break;

            case Feel.FEAR:
                fearTally += 1;
                break;

            case Feel.JOY:
                joyTally += 1;
                break;

            case Feel.LOVE:
                loveTally += 1;
                break;

            case Feel.SADNESS:
                sadnessTally += 1;
                break;

            case Feel.SURPRISE:
                surpriseTally += 1;
                break;

            default:
                Log.e(TAG, "Unsupported feeling attempted to be tallied: " + feeling);
                break;
        }

        boolean addResult = super.add(feel);
        this.sort(feelDateComparator);
        Collections.reverse(this);
        return addResult;
    }

    public Integer getLoveTally() {
        return loveTally;
    }

    public Integer getSadnessTally() {
        return sadnessTally;
    }

    public Integer getSurpriseTally() {
        return surpriseTally;
    }

    public Integer getAngerTally() {
        return angerTally;
    }

    public Integer getFearTally() {
        return fearTally;
    }

    public Integer getJoyTally() {
        return joyTally;
    }
}
