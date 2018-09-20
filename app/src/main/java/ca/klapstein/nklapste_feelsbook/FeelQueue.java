package ca.klapstein.nklapste_feelsbook;

import android.util.Log;

import java.util.PriorityQueue;

/**
 * A PriorityQueue subclass that only accepts {@code Feel}s.
 *
 * Since a PriorityQueue is inherently sorted it provides an easy way to implement the feelings
 * list while retaining order by date
 *
 * Additionally running tallies of each feeling are kept for quick statistics generation.
 */
public class FeelQueue extends PriorityQueue<Feel> {
    private static final String TAG = "FeelQueue";

    private Integer angerTally;
    private Integer fearTally;
    private Integer joyTally;
    private Integer loveTally;
    private Integer sadnessTally;
    private Integer surpriseTally;

    FeelQueue() {
        this.angerTally = 0;
        this.fearTally = 0;
        this.angerTally = 0;
        this.joyTally = 0;
        this.loveTally = 0;
        this.sadnessTally = 0;
        this.surpriseTally = 0;
    }

    @Override
    public boolean remove(Object o) {
        boolean removeResult = super.remove(o);
        if (removeResult && o.getClass().equals(Feel.class)) {
            Feel feel = (Feel) o;
            String feeling = feel.getFeeling();

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
        }
        return removeResult;
    }

    @Override
    public boolean offer(Feel feel) {
        boolean offerResult = super.offer(feel);
        if (offerResult) {
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
