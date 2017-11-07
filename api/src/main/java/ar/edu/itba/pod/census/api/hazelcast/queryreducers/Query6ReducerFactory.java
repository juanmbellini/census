package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.util.ProvinceSet;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * {@link ReducerFactory} for the query 6
 * (i.e returns a {@link Reducer} that holds {@link ar.edu.itba.pod.census.api.models.Province}s
 * by {@code departmentName}, and then counts).
 */
public class Query6ReducerFactory implements ReducerFactory<String, ProvinceSet, Long> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Reducer<ProvinceSet, Long> newReducer(String department) {
        return new Reducer<ProvinceSet, Long>() {

            /**
             * Contains the received {@link ar.edu.itba.pod.census.api.models.Province}s.
             */
            private final ProvinceSet provinces = new ProvinceSet();

            @Override
            public synchronized void reduce(ProvinceSet provinces) {
                this.provinces.addAll(provinces);
            }

            @Override
            public Long finalizeReduce() {
                return (long) provinces.size();
            }
        };
    }
}
