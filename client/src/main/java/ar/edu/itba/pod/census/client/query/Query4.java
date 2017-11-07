package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.OrderByValueCollator;
import ar.edu.itba.pod.census.api.hazelcast.querycollators.SortDirection;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query4CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query4Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query4ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query4 extends HazelcastQuery<Region, Long> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query4(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<Region, Long> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
        return job.mapper(new Query4Mapper<>())
//                .combiner(new Query4CombinerFactory())
                .reducer(new Query4ReducerFactory())
                .submit(new OrderByValueCollator<>(SortDirection.DESC))
                .get();
    }

}
