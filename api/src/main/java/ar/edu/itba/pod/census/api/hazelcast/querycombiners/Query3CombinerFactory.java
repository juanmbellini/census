package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 3
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query3CombinerFactory implements CombinerFactory<Region, BooleanPair, IntegerPair> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<BooleanPair, IntegerPair> newCombiner(Region region) {
        return new Combiner<BooleanPair, IntegerPair>() {

            private int homeless;
            private int working;

            @Override
            public void combine(BooleanPair workingHomelessPair) {
                if (workingHomelessPair.getLeft()) {
                    working++;
                } else if (workingHomelessPair.getRight()) {
                    homeless++;
                }
            }

            @Override
            public IntegerPair finalizeChunk() {
                return new IntegerPair(working, homeless);
            }
    
            @Override
            public void reset() {
                super.reset();
                homeless = 0;
                working = 0;
            }
        };
    }
}