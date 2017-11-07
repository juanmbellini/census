package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import com.hazelcast.mapreduce.Combiner;

/**
 * A {@link Combiner} that counts elements
 *
 * @param <E> The type of element to be counted.
 */
public class UnitCounterCombiner<E> extends Combiner<E, Long> {

    /**
     * Holds the actual count.
     */
    private long count = 0;

    @Override
    public void combine(E unused) {
        count++;
    }

    @Override
    public Long finalizeChunk() {
        return count;
    }

    @Override
    public void reset() {
        this.count = 0;
    }
}
