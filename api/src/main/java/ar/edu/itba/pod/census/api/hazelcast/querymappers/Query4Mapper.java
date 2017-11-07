package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.function.Function;

/**
 * {@link Mapper} for the query 4 (i.e transforms {@link Citizen} into a unit {@link Long}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query4Mapper<K> implements Mapper<K, Citizen, Region, Long> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 344329467838L;

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Query4Mapper.class);

    public void map(K key, Citizen citizen, Context<Region, Long> context) {
        LOGGER.trace("Started mapping...");

        context.emit(toOutKeyFunction().apply(citizen), toOutValueFunction().apply(citizen));

        LOGGER.trace("Finished mapping");
    }


    public Function<Citizen, Region> toOutKeyFunction() {
        return Citizen::getRegion;
    }

    public Function<Citizen, Long> toOutValueFunction(){
        return Citizen::getHomeId;
    }
}
