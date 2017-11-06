package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import com.hazelcast.mapreduce.Combiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link Combiner} that counts elements
 *
 * @param <E> The type of element to be counted.
 */
public class UnitCounterCombiner<E> extends Combiner<E, Long> {

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(UnitCounterCombiner.class);

    /**
     * Holds the actual count.
     */
    private long count = 0;

    @Override
    public void beginCombine() {
        LOGGER.debug("Started combining...");
        super.beginCombine();
    }

    @Override
    public void combine(E unused) {
        LOGGER.trace("Combining...");
        count++;
    }

    @Override
    public Long finalizeChunk() {
        LOGGER.debug("Finished combining. Count is {}", count);
        return count;
    }

    @Override
    public void reset() {
        this.count = 0;
    }
}
