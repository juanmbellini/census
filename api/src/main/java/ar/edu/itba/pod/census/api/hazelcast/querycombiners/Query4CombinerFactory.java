package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.LongSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 4
 * (i.e returns a {@link Combiner} that holds homeIds of the same {@link Region}).
 */
public class Query4CombinerFactory implements CombinerFactory<Region, Long, LongSet> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 8793287331L;


    @Override
    public Combiner<Long, LongSet> newCombiner(Region region) {

        return new Combiner<Long, LongSet>() {

            /**
             * Contains the homeIds for the actual chunk.
             */
            private final LongSet set = new LongSet();

            @Override
            public void combine(Long homeId) {
                set.add(homeId);
            }

            @Override
            public LongSet finalizeChunk() {
                // Send it in a new instance as the one held bu this Combiner will be reset.
                return new LongSet(set);
            }

            @Override
            public void reset() {
                set.clear();
            }
        };
    }
}