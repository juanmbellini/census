package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 2
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query2CombinerFactory implements CombinerFactory<String, Long, Long> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Long, Long> newCombiner(String departmentName) {
        return new UnitCounterCombiner<>();
    }
}