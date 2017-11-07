package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.LongSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 5
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query5CombinerFactory implements CombinerFactory<Region, Long, IntegerPair> {
    
    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Combiner<Long, IntegerPair> newCombiner(Region region) {
        return new Combiner<Long, IntegerPair>() {
            
            private final LongSet homes = new LongSet();
            private int count;
            
            @Override
            public void combine(Long homeId) {
                homes.add(homeId);
                count++;
            }
    
            @Override
            public IntegerPair finalizeChunk() {
                return new IntegerPair(homes.size(), count);
            }
        };
    }
}