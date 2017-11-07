package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * {@link Mapper} for the query 4 (i.e transforms {@link Citizen} into a &lt;{@link Region},{@code homeId}&gt; pair).
 *
 * @param <K> The type of the input key.
 */
public class Query4Mapper<K> implements Mapper<K, Citizen, Region, Long> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 344329467838L;

    @Override
    public void map(K key, Citizen citizen, Context<Region, Long> context) {
        context.emit(citizen.getRegion(), citizen.getHomeId());
    }
}
