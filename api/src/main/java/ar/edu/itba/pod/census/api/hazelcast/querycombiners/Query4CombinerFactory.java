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
             * Holds already sent home ids (in order to not resend them).
             */
            private final LongSet alreadySent = new LongSet();

            /**
             * Contains the homeIds for the actual chunk.
             */
            private final LongSet actualChunkSet = new LongSet();

            @Override
            public void combine(Long homeId) {
                if (!alreadySent.contains(homeId)) {
                    actualChunkSet.add(homeId);
                }
            }

            @Override
            public LongSet finalizeChunk() {
                alreadySent.addAll(actualChunkSet);
                // Send it in a new instance as the one held bu this Combiner will be reset.
                return new LongSet(actualChunkSet);
            }

            @Override
            public void reset() {
                actualChunkSet.clear();
            }

            @Override
            public void finalizeCombine() {
                alreadySent.clear();
            }
        };
    }
}