package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * {@link Mapper} for the query 3 (i.e transforms those {@link Citizen}s working or homeless
 * into a &lt;{@link Region}, {@link BooleanPair},
 * which holds the &lt;{@code isWorking}, {@code isHomeless}&gt; data&gt; pair).
 *
 * @param <K> The type of the input key.
 */
public class Query3Mapper<K> implements Mapper<K, Citizen, Region, BooleanPair> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void map(K key, Citizen citizen, Context<Region, BooleanPair> context) {

        final Boolean isWorking = citizen.getActivityConditionId() == 1;
        final Boolean isHomeless = citizen.getActivityConditionId() == 2;

        // Count citizen only if 'Ocupado' or 'Desocupado'
        if (isWorking || isHomeless) {
            context.emit(citizen.getRegion(), new BooleanPair(isWorking, isHomeless));
        }
    }
}
