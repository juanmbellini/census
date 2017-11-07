package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.Pair;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * {@link CombinerFactory} for the query 3
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query3CombinerFactory implements CombinerFactory<Region, BooleanPair, IntegerPair> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query3CombinerFactory.class);
    
    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Combiner<BooleanPair, IntegerPair> newCombiner(Region region) {
        LOGGER.trace("instantiating combiner");
        return new Combiner<BooleanPair, IntegerPair>() {
            
            private int homeless;
            private int working;
            
            @Override
            public void combine(BooleanPair workingHomelessPair) {
                LOGGER.trace("combining {}", workingHomelessPair);
                if (workingHomelessPair.getLeft()) {
                    working++;
                } else if (workingHomelessPair.getRight()) {
                    homeless++;
                }
            }
    
            @Override
            public IntegerPair finalizeChunk() {
                LOGGER.trace("finalize chunk {}/{}", working, homeless);
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