package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * {@link ReducerFactory} for the query 3
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
 */
public class Query3ReducerFactory implements ReducerFactory<Region, IntegerPair, Double> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query3ReducerFactory.class);
    
    @Override
    public Reducer<IntegerPair, Double> newReducer(final Region region) {
        LOGGER.trace("instantiating reducer");
        return new Reducer<IntegerPair, Double>() {
            
            private int homeless;
            private int working;
            
            @Override
            public void reduce(IntegerPair workingHomelessPair) {
//                LOGGER.trace("reducing {}", workingHomelessPair);
                homeless += workingHomelessPair.getRight();
                working += workingHomelessPair.getLeft();
            }
    
            @Override
            public Double finalizeReduce() {
//                LOGGER.trace("finalize reduce {} {}/{}", region.getName(), homeless, working);
                if (homeless + working == 0) {
                    return 0.0;
                }
                return homeless / (double)(homeless + working);
            }
        };
    }
}
