package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.TopNCollator;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query2CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query2Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query2ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Class representing the {@link Query} with {@code queryId} 2.
 * Implemented using Hazelcast (it extends {@link HazelcastQuery}).
 */
public class Query2 extends HazelcastQuery<String, Long> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query2(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<String, Long> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
        return job.mapper(new Query2Mapper<>(params.getProv()))
                .combiner(new Query2CombinerFactory())
                .reducer(new Query2ReducerFactory())
                .submit(new TopNCollator<>(params.getN()))
                .get();
    }
}
