package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.StringPair;
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
public class Query7Mapper<K> implements Mapper<K, Citizen, StringPair, StringPair> {
	
	/**
	 * Used for serialization of this {@link Mapper}.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The {@link Logger}.
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(Query7Mapper.class);
	
	public void map(K key, Citizen citizen, Context<StringPair, StringPair> context) {
		LOGGER.trace("Started mapping...");
		
		for (int i = 0; i < Province.values().length; i++) {
			if (i < citizen.getProvince().ordinal()) {
				context.emit(
						new StringPair(Province.values()[i].getName(), citizen.getProvince().getName()),
						new StringPair("", citizen.getDepartmentName())
				);
			} else if (i > citizen.getProvince().ordinal()) {
				context.emit(
						new StringPair(citizen.getProvince().getName(), Province.values()[i].getName()),
						new StringPair(citizen.getDepartmentName(), "")
				);
			}
		}
		
//		context.emit(toOutKeyFunction().apply(citizen), citizen.getDepartmentName());
		LOGGER.trace("Finished mapping");
	}
	
}
