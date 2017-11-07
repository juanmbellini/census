package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link CombinerFactory} for the query 4
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query4CombinerFactory implements CombinerFactory<Region, Long, Map<Region, Set<Long>>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Query4CombinerFactory.class);

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 8793287331L;


    @Override
    public Combiner<Long, Map<Region, Set<Long>>> newCombiner(Region region) {

        return new Combiner<Long, Map<Region, Set<Long>>>() {

            private Map<Region, Set<Long>> map = new HashMap<>();

            @Override
            public void beginCombine() {
                super.beginCombine();
                Region.toList().forEach((r) -> map.put(r, new HashSet<>()));
            }

            @Override
            public void combine(Long homeId) {
                LOGGER.trace("Combining homeId {}", homeId);
                map.get(region).add(homeId);
            }

            @Override
            public Map<Region, Set<Long>> finalizeChunk() {
                LOGGER.trace("Finalize chunk");
                return map;
            }

            @Override
            public void reset(){
                this.map = new HashMap<>();
                Region.toList().forEach((r) -> map.put(r, new HashSet<>()));
            }
        };
    }
}