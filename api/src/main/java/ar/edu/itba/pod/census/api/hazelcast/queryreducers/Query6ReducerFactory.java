package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * {@link ReducerFactory} for the query 1
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
 */
public class Query6ReducerFactory implements ReducerFactory<String, StringSet, Long> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Reducer<StringSet, Long> newReducer(String department) {
        return new Reducer<StringSet, Long>() {
            
            private final StringSet provinces = new StringSet();
            
            @Override
            public synchronized void reduce(StringSet _provinces) {
                provinces.addAll(_provinces);
            }
    
            @Override
            public Long finalizeReduce() {
                return (long)provinces.size();
            }
        };
    }
}
