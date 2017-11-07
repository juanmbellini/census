package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.LongLongSetPair;
import ar.edu.itba.pod.census.api.util.LongSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 5
 * (i.e returns a {@link Combiner} that count {@link ar.edu.itba.pod.census.api.models.Citizen}s
 * and holds homeIds of the same {@link Region}).
 */
public class Query5CombinerFactory implements CombinerFactory<Region, Long, LongLongSetPair> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Long, LongLongSetPair> newCombiner(Region region) {
        return new Combiner<Long, LongLongSetPair>() {

            /**
             * Contains the count for the actual chunk.
             */
            private long count = 0;

            /**
             * Contains the homeIds for the actual chunk.
             */
            private final LongSet homes = new LongSet();

            @Override
            public void combine(Long homeId) {
                homes.add(homeId);
                count++;
            }

            @Override
            public LongLongSetPair finalizeChunk() {
                // Send set in a new instance as the one held bu this Combiner will be reset.
                return new LongLongSetPair(count, new LongSet(homes));
            }

            @Override
            public void reset() {
                count = 0;
                homes.clear();
            }
        };
    }
}