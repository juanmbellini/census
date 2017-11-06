package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import com.hazelcast.util.UuidUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * An Abstract {@link Query} that uses Hazelcast to perform them.
 *
 * @param <K> The type of the key of the resulting map of the query.
 * @param <V> The type of the value of the resulting map of the query.
 */
public abstract class HazelcastQuery<K, V> implements Query<K, V> {

    /**
     * The {@link HazelcastInstance} to be used to perform queries.
     */
    private final HazelcastInstance hazelcastInstance;


    /**
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link IMap} is constructed.
     */
    protected HazelcastQuery(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    @Override
    public Map<K, V> perform(List<Citizen> citizens) {
        final JobTracker tracker = hazelcastInstance.getJobTracker(hazelcastInstance.getName());
        final KeyValueSource<String, Citizen> source = KeyValueSource.fromMap(toHazelcastIMap(citizens));
        final Job<String, Citizen> job = tracker.newJob(source);

        return perform(job);
    }

    /**
     * Method that actually performs the query.
     *
     * @param job The Map/Reduce job used to perform the query.
     * @return A {@link Map} holding the query results.
     */
    protected abstract Map<K, V> perform(Job<String, Citizen> job);


    /**
     * Transforms the given {@link List} of {@link Citizen} into an {@link IMap} with key of type {@code E},
     * and value of type {@link Citizen}.
     *
     * @param citizens The {@link List} of {@link Citizen} to be transformed.
     * @return The generated {@link IMap}.
     */
    private IMap<String, Citizen> toHazelcastIMap(List<Citizen> citizens) {
        final IMap<String, Citizen> hazelcastMap = hazelcastInstance.getMap(hazelcastInstance.getName());
        return citizens.stream().collect(new ToIMapCollector<>(UuidUtil::newSecureUuidString, hazelcastMap));
    }

    /**
     * {@link Collector} that will return all input data into a Hazelcast {@link IMap}.
     *
     * @param <T> The type of the key to be used in the resultant {@link IMap}.
     */
    private final class ToIMapCollector<T> implements Collector<Citizen, Map<T, Citizen>, IMap<T, Citizen>> {

        /**
         * {@link Supplier} of keys.
         */
        private final Supplier<T> keyGenerator;

        /**
         * Resultant {@link IMap}, which will hold the input data.
         */
        private final IMap<T, Citizen> iMap;

        /**
         * Constructor.
         *
         * @param keyGenerator A {@link Supplier} of keys for the resultant {@link IMap}. Must be concurrent.
         * @param iMap         The resultant {@link IMap} to which input data will be saved into.
         */
        private ToIMapCollector(Supplier<T> keyGenerator, IMap<T, Citizen> iMap) {
            this.keyGenerator = keyGenerator;
            this.iMap = iMap;
        }

        @Override
        public Supplier<Map<T, Citizen>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<T, Citizen>, Citizen> accumulator() {
            return (map, citizen) -> map.put(keyGenerator.get(), citizen);
        }

        @Override
        public BinaryOperator<Map<T, Citizen>> combiner() {
            return (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            };
        }

        @Override
        public Function<Map<T, Citizen>, IMap<T, Citizen>> finisher() {
            return map -> {
                iMap.putAll(map);
                return iMap;
            };
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.singleton(Characteristics.CONCURRENT);
        }
    }
}
