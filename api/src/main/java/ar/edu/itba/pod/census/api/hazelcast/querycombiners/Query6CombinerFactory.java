package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link CombinerFactory} for the query 5
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query6CombinerFactory implements CombinerFactory<String, String, Long> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query6CombinerFactory.class);
    
    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Combiner<String, Long> newCombiner(final String province) {
        LOGGER.trace("instantiating combiner");
        return new Combiner<String, Long>() {
            
            private final Set<String> provinces = new HashSet<>();
            
            @Override
            public void combine(String province) {
                LOGGER.trace("combining {}", province);
                provinces.add(province);
            }
    
            @Override
            public Long finalizeChunk() {
//                LOGGER.trace("finalize chunk {}/{}", working, homeless);
                return (long)provinces.size();
            }
        };
    }
}