package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.querycombiners.Query1Combiner;
import ar.edu.itba.pod.census.querymappers.Query1Mapper;
import ar.edu.itba.pod.census.queryreducers.Query1Reducer;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Map;

public class Query1 {

    public Map<String, Long> execute(Job<String, String> job) throws Exception {
        ICompletableFuture<Map<String, Long>> future = job
                .mapper(new Query1Mapper())
                .combiner(new Query1Combiner())
                .reducer(new Query1Reducer())
                .submit(); // Collator

        return future.get();
    }
}
