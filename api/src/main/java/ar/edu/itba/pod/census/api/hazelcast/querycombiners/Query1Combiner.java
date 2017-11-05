package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query1Combiner implements CombinerFactory<String, Long, Long> {
    private final static Logger LOGGER = LoggerFactory.getLogger(Query1Combiner.class);
    @Override
    public Combiner<Long, Long> newCombiner(String key ) {
        return new RegionCombiner();
    }

    private class RegionCombiner extends Combiner <Long, Long> {
        private volatile long sum = 0;

        @Override
        public void combine(Long val) {
            sum += val;
        }

        @Override
        public Long finalizeChunk() {
            LOGGER.info("Combiner -> Returning {}", sum);
            return sum;
        }

        @Override
        public void reset() {
            sum = 0;
        }
    }
}