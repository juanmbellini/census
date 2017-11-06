package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.KeyValueSource;
import com.hazelcast.mapreduce.Mapper;

import java.util.function.Function;

/**
 * Interface that extends {@link Mapper} and implements {@link Mapper#map(Object, Object, Context)} as a default method,
 * using {@link #toOutKeyFunction()} method to transform an object of type {@code ValueIn}
 * into an object of type {@code KeyOuy}.
 * This is a {@link Mapper} which must be used to count by a given key (i.e of type {@code KeyOut})
 *
 * @param <KeyIn>   The type of key used in the {@link KeyValueSource} (unused).
 * @param <ValueIn> The type of value used in the {@link KeyValueSource}
 * @param <KeyOut>  The key type for mapped results (type of object to be counted by).
 */
public interface CounterByValueInMapper<KeyIn, ValueIn, KeyOut> extends Mapper<KeyIn, ValueIn, KeyOut, Long> {

    @Override
    default void map(KeyIn keyIn, ValueIn valueIn, Context<KeyOut, Long> context) {
        context.emit(toOutKeyFunction().apply(valueIn), 1L);
    }

    /**
     * Creates a {@link Function} that takes an object of type {@code ValueIn}
     * and transforms it into an object of type {@code KeyOut}.
     * The result of this function will be emitted by the implementation.
     *
     * @return The said function.
     */
    Function<ValueIn, KeyOut> toOutKeyFunction();
}
