package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * {@link ReducerFactory} for the query 3
 * (i.e returns a {@link Reducer} that counts working and homeless {@link ar.edu.itba.pod.census.api.models.Citizen},
 * and calculates the unemployment index).
 */
public class Query3ReducerFactory implements ReducerFactory<Region, IntegerPair, Double> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Reducer<IntegerPair, Double> newReducer(final Region region) {
        return new Reducer<IntegerPair, Double>() {

            /**
             * Contains the working {@link ar.edu.itba.pod.census.api.models.Citizen}s for the actual chunk.
             */
            private int homeless;

            /**
             * Contains the homeless {@link ar.edu.itba.pod.census.api.models.Citizen}s for the actual chunk.
             */
            private int working;

            @Override
            public synchronized void reduce(IntegerPair workingHomelessPair) {
                homeless += workingHomelessPair.getRight();
                working += workingHomelessPair.getLeft();
            }

            @Override
            public Double finalizeReduce() {
                if (homeless + working == 0) {
                    return 0.0;
                }
                return homeless / (double) (homeless + working);
            }
        };
    }
}
