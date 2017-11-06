package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Query1Mapper.class);

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
