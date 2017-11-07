package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.models.Citizen;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutionException;
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
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(HazelcastQuery.class);

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
    public Map<K, V> perform(List<Citizen> citizens, QueryParamsContainer queryParams) {
        final JobTracker tracker = hazelcastInstance.getJobTracker(hazelcastInstance.getName());
        final IMap<Long, Citizen> hazelcastMap = toHazelcastIMap(citizens);
        final KeyValueSource<Long, Citizen> source = KeyValueSource.fromMap(hazelcastMap);
        final Job<Long, Citizen> job = tracker.newJob(source);
        try {
            return perform(job, queryParams);
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.debug("Could not perform query. Exception message: {}", e.getMessage());
            LOGGER.trace("Exception stacktrace: ", e);
            throw new RuntimeException("Could not perform map/reduce job", e);
        }
    }

    /**
     * Method that actually performs the query.
     *
     * @param job The Map/Reduce job used to perform the query.
     * @return A {@link Map} holding the query results.
     */
    protected abstract Map<K, V> perform(Job<Long, Citizen> job, QueryParamsContainer queryParams)
            throws ExecutionException, InterruptedException;


    /**
     * Transforms the given {@link List} of {@link Citizen} into an {@link IMap} with key of type {@code E},
     * and value of type {@link Citizen}.
     *
     * @param citizens The {@link List} of {@link Citizen} to be transformed.
     * @return The generated {@link IMap}.
     */
    private synchronized IMap<Long, Citizen> toHazelcastIMap(List<Citizen> citizens) {
        final IMap<Long, Citizen> hazelcastMap = hazelcastInstance.getMap(hazelcastInstance.getName());
        hazelcastMap.clear(); // Must clear remote map
        return citizens.stream()
                .parallel() // Make it faster
                .collect(new ToIMapCollector<>(() -> new SecureRandom().nextLong(), hazelcastMap, citizens.size()));
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
         * The size the final map must have before inserting all into the {@link IMap}.
         * Used to validate that all input data was collected.
         */
        private final long mustHaveSize;

        /**
         * Constructor.
         *
         * @param keyGenerator A {@link Supplier} of keys for the resultant {@link IMap}. Must be concurrent.
         * @param iMap         The resultant {@link IMap} to which input data will be saved into.
         * @param mustHaveSize The size the final map must have before inserting all into the {@link IMap}.
         *                     Used to validate that all input data was collected.
         */
        private ToIMapCollector(Supplier<T> keyGenerator, IMap<T, Citizen> iMap, long mustHaveSize) {
            this.keyGenerator = keyGenerator;
            this.iMap = iMap;
            this.mustHaveSize = mustHaveSize;
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
                if (map.size() != mustHaveSize) {
                    throw new IllegalStateException("Could not collect into IMap all input data to be processed");
                }
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
