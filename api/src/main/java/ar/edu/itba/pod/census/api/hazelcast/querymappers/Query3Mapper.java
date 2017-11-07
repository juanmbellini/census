package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

import ar.edu.itba.pod.census.api.util.BooleanPair;
import ar.edu.itba.pod.census.api.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;

/**
 * {@link Mapper} for the query 3 (i.e transforms {@link Citizen} into a unit {@link java.util.Map.Entry}).
 *
 * @param <K> The type of the input and output key.
 */
public class Query3Mapper<K> implements Mapper<K, Citizen, Region, BooleanPair> {
	
	/**
	 * Used for serialization of this {@link Mapper}.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The {@link Logger}.
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(Query3Mapper.class);
	
	public void map(K key, Citizen citizen, Context<Region, BooleanPair> context) {
		LOGGER.trace("Started mapping...");
		
		final Boolean isWorking = citizen.getActivityConditionId() == 1;
		final Boolean isHomeless = citizen.getActivityConditionId() == 2;
		
		// Count citizen only if 'Ocupado' or 'Desocupado'
		if (isWorking || isHomeless) {
			
			LOGGER.trace("emitting {} ({}, {})", citizen.getRegion().getName(), isWorking, isHomeless);
			
			context.emit(toOutKeyFunction().apply(citizen), new BooleanPair(isWorking, isHomeless));
		}
		LOGGER.trace("Finished mapping");
	}
	
	public Function<Citizen, Region> toOutKeyFunction() {
		return Citizen::getRegion;
	}
}
