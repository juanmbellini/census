package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.LongLongSetPair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link ReducerFactory} for the query 5
 * (i.e returns a {@link Reducer} that holds homeIds and count {@link ar.edu.itba.pod.census.api.models.Citizen}s
 * by {@link Region}, and then calculates
 * the average amount of {@link ar.edu.itba.pod.census.api.models.Citizen} per home of each {@link Region}).
 */
public class Query5ReducerFactory implements ReducerFactory<Region, LongLongSetPair, Double> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Reducer<LongLongSetPair, Double> newReducer(final Region region) {
        return new Reducer<LongLongSetPair, Double>() {

            private final Set<Long> homes = new HashSet<>();
            private long count;

            @Override
            public synchronized void reduce(LongLongSetPair pair) {
                homes.addAll(pair.getRight());
                count += pair.getLeft();
            }

            @Override
            public Double finalizeReduce() {
                if (homes.size() == 0) {
                    return 0.0;
                }
                return (double) count / homes.size();
            }
        };
    }
}
