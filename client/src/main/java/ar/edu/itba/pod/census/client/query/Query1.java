package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.OrderByValueCollator;
import ar.edu.itba.pod.census.api.hazelcast.querycollators.SortDirection;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query1CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query1Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query1ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Class representing the {@link Query} with {@code queryId} 1.
 * Implemented using Hazelcast (it extends {@link HazelcastQuery}).
 */
public class Query1 extends HazelcastQuery<Region, Long> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query1(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<Region, Long> perform(Job<Long, Citizen> job, QueryParamsContainer unused)
            throws ExecutionException, InterruptedException {
        return job.mapper(new Query1Mapper<>())
                .combiner(new Query1CombinerFactory())
                .reducer(new Query1ReducerFactory())
                .submit(new OrderByValueCollator<>(SortDirection.DESC))
                .get();
    }
}
