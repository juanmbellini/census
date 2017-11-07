package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.util.ProvinceSet;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * {@link ReducerFactory} for the query 1
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
 */
public class Query6ReducerFactory implements ReducerFactory<String, ProvinceSet, Long> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Reducer<ProvinceSet, Long> newReducer(String department) {
        return new Reducer<ProvinceSet, Long>() {

            private final ProvinceSet provinces = new ProvinceSet();

            @Override
            public synchronized void reduce(ProvinceSet _provinces) {
                provinces.addAll(_provinces);
            }

            @Override
            public Long finalizeReduce() {
                return (long) provinces.size();
            }
        };
    }
}
