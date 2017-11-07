package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 3
 * (i.e returns a {@link Combiner} that count working and homeless {@link ar.edu.itba.pod.census.api.models.Citizen}
 * of the same {@link Region}).
 */
public class Query3CombinerFactory implements CombinerFactory<Region, Boolean, IntegerPair> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Boolean, IntegerPair> newCombiner(Region region) {
        return new Combiner<Boolean, IntegerPair>() {

            /**
             * Contains the working {@link ar.edu.itba.pod.census.api.models.Citizen}s for the actual chunk.
             */
            private int working = 0;

            /**
             * Contains the homeless {@link ar.edu.itba.pod.census.api.models.Citizen}s for the actual chunk.
             */
            private int homeless = 0;

            @Override
            public void combine(Boolean isWorking) {
                if (isWorking) {
                    working++;
                } else {
                    homeless++;
                }
            }

            @Override
            public IntegerPair finalizeChunk() {
                return new IntegerPair(working, homeless);
            }

            @Override
            public void reset() {
                homeless = 0;
                working = 0;
            }
        };
    }
}