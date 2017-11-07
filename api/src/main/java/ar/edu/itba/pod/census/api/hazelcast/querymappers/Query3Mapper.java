package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.function.Function;

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
	
	public void map(K key, Citizen citizen, Context<Region, BooleanPair> context) {
		
		final Boolean isWorking = citizen.getActivityConditionId() == 1;
		final Boolean isHomeless = citizen.getActivityConditionId() == 2;
		
		// Count citizen only if 'Ocupado' or 'Desocupado'
		if (isWorking || isHomeless) {
			context.emit(toOutKeyFunction().apply(citizen), new BooleanPair(isWorking, isHomeless));
		}
	}
	
	public Function<Citizen, Region> toOutKeyFunction() {
		return Citizen::getRegion;
	}
}
