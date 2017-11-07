package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query3CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query5CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query3Mapper;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query5Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query3ReducerFactory;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query5ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query5 extends HazelcastQuery<Region, Double> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query5(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<Region, Double> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
        return job.mapper(new Query5Mapper<>())
                .combiner(new Query5CombinerFactory())
                .reducer(new Query5ReducerFactory())
                .submit()
                .get();
    }
}
