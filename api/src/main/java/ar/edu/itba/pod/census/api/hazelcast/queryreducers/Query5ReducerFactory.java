package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link ReducerFactory} for the query 5
 * (i.e returns a {@link Reducer} that counts input that was produced by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.UnitCounterCombiner}, which is returned by
 * the {@link ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory}).
 */
public class Query5ReducerFactory implements ReducerFactory<Region, Long, Double> {

    /**
     * Used for serialization of this {@link ReducerFactory}.
     */
    private static final long serialVersionUID = 1L;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Query5ReducerFactory.class);
    
    @Override
    public Reducer<Long, Double> newReducer(final Region region) {
        LOGGER.trace("instantiating reducer");
        return new Reducer<Long, Double>() {
            
            private final Set<Long> homes = new HashSet<>();
            private int count;
            
            @Override
            public synchronized void reduce(Long homeId) {
                homes.add(homeId);
                count++;
            }
    
            @Override
            public Double finalizeReduce() {
                if (homes.size() == 0) {
                    return 0.0;
                }
                return (double)count / homes.size();
            }
        };
    }
}
