package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.function.Function;

/**
 * {@link Mapper} for the query 1 (i.e transforms {@link Citizen} into a unit {@link Long}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query1Mapper<K> implements CounterByValueInMapper<K, Citizen, Region> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void map(K key, Citizen citizen, Context<Region, Long> context) {
        CounterByValueInMapper.super.map(key, citizen, context);
    }

    @Override
    public Function<Citizen, Region> toOutKeyFunction() {
        return Citizen::getRegion;
    }
}
