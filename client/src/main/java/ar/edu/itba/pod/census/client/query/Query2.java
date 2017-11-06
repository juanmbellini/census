package ar.edu.itba.pod.census.client.query;

import java.util.Map;

import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query2Combiner;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query2Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query2Reducer;

public class Query2 {

    public Map<String, Long> execute(Job<String, String> job) throws Exception {
        ICompletableFuture<Map<String, Long>> future = job
                .mapper(new Query2Mapper())
                .combiner(new Query2Combiner())
                .reducer(new Query2Reducer())
                .submit(); // Collator

        return future.get();
    }
}
