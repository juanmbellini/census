package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

/**
 * {@link Mapper} for the query 5 (i.e transforms {@link Citizen} into a unit {@link Map.Entry}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query6Mapper<K> implements Mapper<K, Citizen, String, String> {
	
	/**
	 * Used for serialization of this {@link Mapper}.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The {@link Logger}.
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(Query6Mapper.class);
	
	public void map(K key, Citizen citizen, Context<String, String> context) {
		LOGGER.trace("Started mapping...");
		
		context.emit(toOutKeyFunction().apply(citizen), citizen.getProvince().getName());
		LOGGER.trace("Finished mapping");
	}
	
	public Function<Citizen, String> toOutKeyFunction() {
		return Citizen::getDepartmentName;
	}
}
