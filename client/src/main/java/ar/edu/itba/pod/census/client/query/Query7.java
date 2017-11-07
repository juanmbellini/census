package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.TopWithMinNCollator;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query7Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query7ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.util.StringPair;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Class representing the {@link Query} with {@code queryId} 7.
 * Implemented using Hazelcast (it extends {@link HazelcastQuery}).
 */
public class Query7 extends HazelcastQuery<String, Long> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query7(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<String, Long> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
        if (params.getN() == null) {
            throw new IllegalArgumentException("The n query param must be specified for query 7");
        }
        return job.mapper(new Query7Mapper<>())
//				.combiner(new Query7CombinerFactory())
                .reducer(new Query7ReducerFactory())
                .submit(new TopWithMinNCollator<>(params.getN()))
                .get().entrySet().stream()
                .map(entry ->
                        new AbstractMap.SimpleEntry<>(entry.getKey().getLeft() + " + " + entry.getKey().getRight(),
                                entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldV, newV) -> oldV, LinkedHashMap::new))
                ;
    }
}
