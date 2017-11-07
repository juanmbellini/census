package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 5
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query6CombinerFactory implements CombinerFactory<String, String, StringSet> {
    
    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Combiner<String, StringSet> newCombiner(final String departmentName) {
        return new Combiner<String, StringSet>() {
            
            private final StringSet provinces = new StringSet();
            
            @Override
            public void combine(String province) {
                provinces.add(province);
            }
    
            @Override
            public StringSet finalizeChunk() {
                return provinces;
            }
    
            @Override
            public void reset() {
                provinces.clear();
            }
        };
    }
}