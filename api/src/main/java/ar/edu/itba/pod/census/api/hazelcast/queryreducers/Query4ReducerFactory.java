package ar.edu.itba.pod.census.api.hazelcast.queryreducers;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Query4ReducerFactory implements ReducerFactory<Region, Set<Long>, Map<Region, Long>> {


    @Override
    public Reducer<Set<Long>, Map<Region, Long>> newReducer(Region region) {
        return new Reducer<Set<Long>, Map<Region, Long>>() {

            Map<Region, Set<Long>> map = new ConcurrentHashMap<>();

            @Override
            public void beginReduce() {
                Region.toList().forEach((r) -> map.put(r, new HashSet<>()));
            }

            @Override
            public void reduce(Set<Long> set) {
                set.forEach((v) -> map.get(region).add(v));
            }

            @Override
            public Map<Region, Long> finalizeReduce() {
                Map<Region, Long> result = new HashMap<>();
                Region.toList().forEach((r) -> result.put(r, Long.valueOf(map.get(r).size())));
                return result;
            }
        };
    }

}
