package ca.klapstein.nklapste_feelsbook;

import java.util.HashMap;
import java.util.TreeSet;


/**
 * A PriorityQueue subclass that only accepts {@code Feel}s.
 * <p>
 * Since a PriorityQueue is inherently sorted it provides an easy way to implement the feelings
 * list while retaining order by date.
 * <p>
 * Additionally running tallies of each feeling are kept for quick statistics generation.
 */
public class FeelTreeSet extends TreeSet<Feel> {
    private static final String TAG = "FeelTreeSet";

    private HashMap<Feel.Feeling, Integer> feelingTallies;

    FeelTreeSet() {
        feelingTallies = new HashMap<>();
        // initialize a HashMap of the feeling tallies all at 0
        for (Feel.Feeling feel : Feel.Feeling.values()) {
            feelingTallies.put(feel, 0);
        }
    }

    /**
     * Attempt to remove an object from the FeelTreeSet.
     * <p>
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
            Integer feelingTally = feelingTallies.get(feel.getFeeling());
            feelingTally = feelingTally - 1;
            feelingTallies.put(feel.getFeeling(), feelingTally);
        }
        return removeResult;
    }

    /**
     * Inserts the specified {@code Feel} into this FeelTreeSet.
     * <p>
     * If it is successfully inserted increment the tally of the feel inserted.
     *
     * @param feel {@code Feel}
     * @return {@code boolean}
     */
    @Override
    public boolean add(Feel feel) {
        boolean offerResult = super.add(feel);
        if (offerResult) {
            Integer feelingTally = feelingTallies.get(feel.getFeeling());
            feelingTally = feelingTally + 1;
            feelingTallies.put(feel.getFeeling(), feelingTally);
        }
        return offerResult;
    }

    public HashMap<Feel.Feeling, Integer> getFeelingTallies() {
        return feelingTallies;
    }
}
