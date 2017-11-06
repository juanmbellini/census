package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 1
 * (i.e returns a {@link Combiner} that count elements of the same {@link Region}).
 */
public class Query1CombinerFactory implements CombinerFactory<Region, Long, Long> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Long, Long> newCombiner(Region region) {
        return new UnitCounterCombiner<>();
    }
}