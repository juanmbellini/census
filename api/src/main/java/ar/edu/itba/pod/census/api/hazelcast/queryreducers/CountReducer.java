package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import com.hazelcast.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reducer that counts.
 */
public class CountReducer extends Reducer<Long, Long> {

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CountReducer.class);

    /**
     * Holds the count that was informed to this reducer.
     */
    private long count = 0;

    @Override
    public void beginReduce() {
        LOGGER.trace("Started reducing...");
        super.beginReduce();
    }

    @Override
    public void reduce(Long partialCount) {
        LOGGER.trace("Reducing...");
        this.count += partialCount;
    }

    @Override
    public Long finalizeReduce() {
        LOGGER.trace("Finished reducing. Final result is {}", this.count);
        return this.count;
    }
}
