package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.StringSet;
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
public class Query6CombinerFactory implements CombinerFactory<String, String, StringSet> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query6CombinerFactory.class);
    
    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Combiner<String, StringSet> newCombiner(final String departmentName) {
        LOGGER.trace("instantiating combiner");
        return new Combiner<String, StringSet>() {
            
            private final StringSet provinces = new StringSet();
            
            @Override
            public void combine(String province) {
                LOGGER.trace("combining {}", province);
                provinces.add(province);
            }
    
            @Override
            public StringSet finalizeChunk() {
//                LOGGER.debug("dn {}, # = {}, {}", departmentName, provinces.size(), provinces);
                return provinces;
            }
    
            @Override
            public void reset() {
                provinces.clear();
            }
        };
    }
}