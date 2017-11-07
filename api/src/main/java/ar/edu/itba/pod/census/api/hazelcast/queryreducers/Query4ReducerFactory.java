package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashSet;
import java.util.Set;

public class Query4ReducerFactory implements ReducerFactory<Region, Long, Long> {


    @Override
    public Reducer<Long, Long> newReducer(Region region) {
        return new Reducer<Long, Long>() {

            private final Set<Long> homes = new HashSet<>();

            @Override
            public void reduce(Long homeId) {
                homes.add(homeId);
            }

            @Override
            public Long finalizeReduce() {
                return Long.valueOf(homes.size());
            }
        };
    }

}
