package ar.edu.itba.pod.census.queryreducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query1Reducer implements ReducerFactory<String, Long, Long> {

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
            return sum;
        }

    }
}
