package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.LongSet;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashSet;
import java.util.Set;

public class Query4ReducerFactory implements ReducerFactory<Region, LongSet, Long> {


    @Override
    public Reducer<LongSet, Long> newReducer(Region region) {
        return new Reducer<LongSet, Long>() {

            /**
             * Contains the received homeIds.
             */
            private final Set<Long> homes = new HashSet<>();

            @Override
            public synchronized void reduce(LongSet homeId) {
                homes.addAll(homeId);
            }

            @Override
            public Long finalizeReduce() {
                return (long) homes.size();
            }
        };
    }

}
