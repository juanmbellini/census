package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import com.hazelcast.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

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
    private AtomicLong count = new AtomicLong(0);

    @Override
    public void beginReduce() {
        LOGGER.trace("Started reducing...");
        super.beginReduce();
    }

    @Override
    public void reduce(Long partialCount) {
        LOGGER.trace("Reducing...");
        this.count.addAndGet(partialCount);
    }

    @Override
    public Long finalizeReduce() {
        LOGGER.trace("Finished reducing. Final result is {}", this.count);
        return this.count.longValue();
    }
}
