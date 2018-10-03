package ca.klapstein.nklapste_feelsbook;

import java.util.HashMap;
import java.util.TreeSet;


/**
 * A TreeSet subclass that only accepts {@code Feel}s.
 * <p>
 * Since a TreeSet is inherently sorted it provides an easy way to implement the feelings
 * list while retaining order by date.
 * <p>
 * Additionally running tallies of each feeling are kept for quick statistics generation.
 * <p>
 * One limitation of using a TreeSet however is that no two feels can have the exact same date.
 * I deemed this as a reasonable sacrifice.
 */
public class FeelTreeSet extends TreeSet<Feel> {
    private static final String TAG = "FeelTreeSet";

    private HashMap<Feeling, Integer> feelingTallies;

    FeelTreeSet() {
        feelingTallies = new HashMap<>();
        // initialize a HashMap of the feeling tallies all at 0
        for (Feeling feel : Feeling.values()) {
            feelingTallies.put(feel, 0);
        }
    }

    /**
     * Attempt to remove an object from the FeelTreeSet.
     * <p>
     * If it is successfully removed decrement the tally of feel removed.
     *
     * @param obj {@code Object}
     * @return {@code boolean}
     */
    @Override
    public boolean remove(Object obj) {
        boolean removeResult = super.remove(obj);
        if (removeResult && obj.getClass().equals(Feel.class)) {
            Feel feel = (Feel) obj;
            feelingTallies.put(feel.getFeeling(), feelingTallies.get(feel.getFeeling()) - 1);
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
            feelingTallies.put(feel.getFeeling(), feelingTallies.get(feel.getFeeling()) + 1);
        }
        return offerResult;
    }

    public HashMap<Feeling, Integer> getFeelingTallies() {
        return feelingTallies;
    }
}
