package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link CombinerFactory} for the query 4
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query4CombinerFactory implements CombinerFactory<Region, Long, Set<Long>> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 8793287331L;


    @Override
    public Combiner<Long, Set<Long>> newCombiner(Region region) {

        return new Combiner<Long, Set<Long>>() {

            private Set<Long> set = new HashSet<>();

            @Override
            public void combine(Long homeId) {
                set.add(homeId);
            }

            @Override
            public Set<Long> finalizeChunk() {
                return set;
            }

            @Override
            public void reset(){
                this.set = new HashSet<>();
            }
        };
    }
}