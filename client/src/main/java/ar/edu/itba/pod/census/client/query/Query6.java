package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.TopWithMinNCollator;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query6CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query6Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query6ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Class representing the {@link Query} with {@code queryId} 6.
 * Implemented using Hazelcast (it extends {@link HazelcastQuery}).
 */
public class Query6 extends HazelcastQuery<String, Long> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query6(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<String, Long> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
        if (params.getN() == null) {
            throw new IllegalArgumentException("The n query param must be specified for query 6");
        }
        return job.mapper(new Query6Mapper<>())
                .combiner(new Query6CombinerFactory())
                .reducer(new Query6ReducerFactory())
                .submit(new TopWithMinNCollator<>(params.getN()))
                .get();
    }
}
