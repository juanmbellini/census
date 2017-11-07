package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.function.Function;

/**
 * {@link Mapper} for the query 2 (i.e transforms {@link Citizen} into a unit {@link Long}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query2Mapper<K> implements CounterByValueInMapper<K, Citizen, String> {

    /**
     * Used for serialization of this {@link Mapper}.
     */
    private static final long serialVersionUID = 1L;

    private String provinceName; 
    
    public Query2Mapper(final String provinceName) {
    	this.provinceName = provinceName;
		}
    
    @Override
    public void map(K key, Citizen citizen, Context<String, Long> context) {
        if (citizen.getProvince().getName().equals(this.provinceName)) {
        	CounterByValueInMapper.super.map(key, citizen, context);
        }
    }

    @Override
    public Function<Citizen, String> toOutKeyFunction() {
        return Citizen::getDepartmentName;
    }
}
