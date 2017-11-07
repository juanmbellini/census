package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 3
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query3CombinerFactory implements CombinerFactory<Region, Boolean, IntegerPair> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Boolean, IntegerPair> newCombiner(Region region) {
        return new Combiner<Boolean, IntegerPair>() {

            private int homeless;
            private int working;

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