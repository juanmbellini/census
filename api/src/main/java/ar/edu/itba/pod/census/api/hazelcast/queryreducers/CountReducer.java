package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import com.hazelcast.mapreduce.Reducer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Reducer that counts.
 */
public class CountReducer extends Reducer<Long, Long> {


    /**
     * Holds the count that was informed to this reducer.
     */
    private AtomicLong count = new AtomicLong(0);

    @Override
    public void reduce(Long partialCount) {
        this.count.addAndGet(partialCount);
    }

    @Override
    public Long finalizeReduce() {
        return this.count.longValue();
    }
}
