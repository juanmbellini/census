package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.function.Function;

/**
 * {@link Mapper} for the query 6 (i.e transforms {@link Citizen}
 * into a &lt;{@code departmentName}, {@code provinceName}&gt; pair).
 *
 * @param <K> The type of the input key.
 */
public class Query6Mapper<K> implements Mapper<K, Citizen, String, String> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    public void map(K key, Citizen citizen, Context<String, String> context) {
        context.emit(toOutKeyFunction().apply(citizen), citizen.getProvince().getName());
    }

    public Function<Citizen, String> toOutKeyFunction() {
        return Citizen::getDepartmentName;
    }
}
