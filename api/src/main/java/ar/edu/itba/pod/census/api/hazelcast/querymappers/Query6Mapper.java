package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Province;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * {@link Mapper} for the query 6 (i.e transforms {@link Citizen}
 * into a &lt;{@code departmentName}, {@link Province}&gt; pair).
 *
 * @param <K> The type of the input key.
 */
public class Query6Mapper<K> implements Mapper<K, Citizen, String, Province> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void map(K key, Citizen citizen, Context<String, Province> context) {
        context.emit(citizen.getDepartmentName(), citizen.getProvince());
    }
}
