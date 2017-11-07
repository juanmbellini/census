package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * {@link Mapper} for the query 3 (i.e transforms those {@link Citizen}s working or homeless
 * into a &lt;{@link Region}, {@link Boolean}&gt; pair, being {@code true}if its working, or {@code false} otherwise).
 *
 * @param <K> The type of the input key.
 */
public class Query3Mapper<K> implements Mapper<K, Citizen, Region, Boolean> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void map(K key, Citizen citizen, Context<Region, Boolean> context) {

        final int activityConditionId = citizen.getActivityConditionId();
        // Filter by activityCondition (Occupied or Not occupied)
        if (activityConditionId == 1 || activityConditionId == 2) {
            context.emit(citizen.getRegion(), activityConditionId == 1);
        }
    }
}
