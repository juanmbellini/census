package ar.edu.itba.pod.census.api.util;

import java.util.HashSet;

/**
 * A concrete extension of {@link HashSet} for {@link Long}.
 */
public class LongSet extends HashSet<Long> {

    /**
     * Default constructor.
     */
    public LongSet() {

    }

    /**
     * Constructor from another set.
     *
     * @param fromSet Another set of this type from which data will be added.
     */
    public LongSet(LongSet fromSet) {
        super(fromSet);
    }
}
