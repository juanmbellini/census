package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashSet;
import java.util.Set;

public class Query4ReducerFactory implements ReducerFactory<Region, Set<Long>, Long> {


    @Override
    public Reducer<Set<Long>, Long> newReducer(Region region) {
        return new Reducer<Set<Long>, Long>() {

            Set<Long> set = new HashSet<>();

            @Override
            public void reduce(Set<Long> incomingSet) {
                incomingSet.forEach((v) -> set.add(v));
            }

            @Override
            public Long finalizeReduce() {
                return Long.valueOf(set.size());
            }
        };
    }

}
