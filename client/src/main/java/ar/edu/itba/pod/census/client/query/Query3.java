package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.hazelcast.querycollators.OrderByValueCollator;
import ar.edu.itba.pod.census.api.hazelcast.querycollators.SortDirection;
import ar.edu.itba.pod.census.api.hazelcast.querycombiners.Query3CombinerFactory;
import ar.edu.itba.pod.census.api.hazelcast.querymappers.Query3Mapper;
import ar.edu.itba.pod.census.api.hazelcast.queryreducers.Query3ReducerFactory;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Class representing the {@link Query} with {@code queryId} 3.
 * Implemented using Hazelcast (it extends {@link HazelcastQuery}).
 */
public class Query3 extends HazelcastQuery<Region, String> {

    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     */
    public Query3(HazelcastInstance hazelcastInstance) {
        super(hazelcastInstance);
    }

    @Override
    protected Map<Region, String> perform(Job<Long, Citizen> job, QueryParamsContainer params)
            throws ExecutionException, InterruptedException {
        return job.mapper(new Query3Mapper<>())
                .combiner(new Query3CombinerFactory())
                .reducer(new Query3ReducerFactory())
                .submit(new OrderByValueCollator<>(SortDirection.DESC))
                .get()
                .entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(),
                        new DecimalFormat("#.##").format(Math.floor(entry.getValue() * 100) / 100)
                                .replace(",", ".")))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldV, newV) -> oldV, LinkedHashMap::new));
    }
}
