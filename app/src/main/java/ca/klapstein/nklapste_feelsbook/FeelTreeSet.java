package ca.klapstein.nklapste_feelsbook;

import android.util.Log;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Anger;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Fear;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Joy;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Love;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Sadness;
import static ca.klapstein.nklapste_feelsbook.Feel.Feelings.Surprise;

/**
 * A PriorityQueue subclass that only accepts {@code Feel}s.
 *
 * Since a PriorityQueue is inherently sorted it provides an easy way to implement the feelings
 * list while retaining order by date.
 *
 * Additionally running tallies of each feeling are kept for quick statistics generation.
 */
public class FeelTreeSet extends TreeSet<Feel> {
    private static final String TAG = "FeelTreeSet";

    private Integer angerTally;
    private Integer fearTally;
    private Integer joyTally;
    private Integer loveTally;
    private Integer sadnessTally;
    private Integer surpriseTally;

    FeelTreeSet() {
        this.angerTally = 0;
        this.fearTally = 0;
        this.angerTally = 0;
        this.joyTally = 0;
        this.loveTally = 0;
        this.sadnessTally = 0;
        this.surpriseTally = 0;
    }

    /**
     * Attempt to remove an object from the FeelTreeSet.
     *
     * If it is successfully removed decrement the tally of feel removed.
     *
     * @param o {@code Object}
     * @return {@code boolean}
     */
    @Override
    public boolean remove(Object o) {
        boolean removeResult = super.remove(o);
        if (removeResult && o.getClass().equals(Feel.class)) {
            Feel feel = (Feel) o;

            switch (feel.getFeeling()) {
                case Anger:
                    angerTally -= 1;
                    break;

                case Fear:
                    fearTally -= 1;
                    break;

                case Joy:
                    joyTally -= 1;
                    break;

                case Love:
                    loveTally -= 1;
                    break;

                case Sadness:
                    sadnessTally -= 1;
                    break;

                case Surprise:
                    surpriseTally -= 1;
                    break;

                default:
                    Log.e(TAG, "Unsupported feeling attempted to be tallied: " + feel.getFeeling());
                    break;
            }
        }
        return removeResult;
    }

    /**
     * Inserts the specified {@code Feel} into this FeelTreeSet.
     *
     * If it is successfully inserted increment the tally of the feel inserted.
     *
     * @param feel {@code Feel}
     * @return {@code boolean}
     */
    @Override
    public boolean add(Feel feel) {
        boolean offerResult = super.add(feel);
        if (offerResult) {
            switch (feel.getFeeling()) {
                case Anger:
                    angerTally += 1;
                    break;

                case Fear:
                    fearTally += 1;
                    break;

                case Joy:
                    joyTally += 1;
                    break;

                case Love:
                    loveTally += 1;
                    break;

                case Sadness:
                    sadnessTally += 1;
                    break;

                case Surprise:
                    surpriseTally += 1;
                    break;

                default:
                    Log.e(TAG, "Unsupported feeling attempted to be tallied: " + feel.getFeeling());
                    break;
            }
        }
        return offerResult;
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
