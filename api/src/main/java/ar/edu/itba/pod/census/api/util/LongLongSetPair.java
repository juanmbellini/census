package ar.edu.itba.pod.census.api.util;

/**
 * A {@link Pair} holding a {@link Long} and a {@link LongSet}.
 */
public class LongLongSetPair extends Pair<Long, LongSet> {

    /**
     * Constructor.
     *
     * @param l The left {@link Long} of the {@link Pair}.
     * @param r The right {@link LongSet} of the {@link Pair}.
     */
    public LongLongSetPair(Long l, LongSet r) {
        super(l, r);
    }
}
