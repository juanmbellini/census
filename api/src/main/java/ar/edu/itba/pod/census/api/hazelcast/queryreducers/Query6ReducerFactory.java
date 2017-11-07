package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * {@link ReducerFactory} for the query 1
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
 */
public class Query6ReducerFactory implements ReducerFactory<String, Long, Long> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    
    @Override
    public Reducer<Long, Long> newReducer(String department) {
        return new Reducer<Long, Long>() {
            
            private long count;
            
            @Override
            public void reduce(Long c) {
                count += c;
            }
    
            @Override
            public Long finalizeReduce() {
                return count;
            }
        };
    }
}
