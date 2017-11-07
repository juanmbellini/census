package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link CombinerFactory} for the query 5
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query7CombinerFactory implements CombinerFactory<Province, String, StringSet> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query7CombinerFactory.class);
    
    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Combiner<String, StringSet> newCombiner(final Province province) {
        LOGGER.trace("instantiating combiner");
        return new Combiner<String, StringSet>() {
            
            private final StringSet departments = new StringSet();
            
            @Override
            public void combine(String department) {
                LOGGER.trace("combining {}", departments);
                departments.add(department);
            }
    
            @Override
            public StringSet finalizeChunk() {
//                LOGGER.debug("dn {}, # = {}, {}", departmentName, provinces.size(), provinces);
                return departments;
            }
        };
    }
}