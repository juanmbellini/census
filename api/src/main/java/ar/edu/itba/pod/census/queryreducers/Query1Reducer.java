package ar.edu.itba.pod.census.queryreducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query1Reducer implements ReducerFactory<String, Long, Long> {
    private final static Logger LOGGER = LoggerFactory.getLogger(Query1Reducer.class);

    @Override
    public Reducer<Long, Long> newReducer(String s) {
        return new RegionCounter();
    }

    private class RegionCounter extends Reducer<Long, Long> {
        private volatile long sum;

        @Override
        public void beginReduce() {
            sum = 0;
        }

        @Override
        public void reduce(Long value) {
            sum += value;
        }

        @Override
        public Long finalizeReduce() {
            LOGGER.info("Reducer returning {} for Query1", sum);
            return sum;
        }

    }
}
