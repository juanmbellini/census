package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;

/**
 * {@link Mapper} for the query 2 (i.e transforms {@link Citizen} into a unit {@link Long}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query3Mapper<K> implements CounterByValueInMapper<K, Citizen, Region> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Query3Mapper.class);
    
    @Override
    public void map(K key, Citizen citizen, Context<Region, Long> context) {
        LOGGER.trace("Started mapping...");
        CounterByValueInMapper.super.map(key, citizen, context);
        LOGGER.trace("Finished mapping");
    }

    @Override
    public Function<Citizen, Region> toOutKeyFunction() {
        return Citizen::getRegion;
    }
}
