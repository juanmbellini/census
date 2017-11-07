package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.LongLongSetPair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link ReducerFactory} for the query 5
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
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
