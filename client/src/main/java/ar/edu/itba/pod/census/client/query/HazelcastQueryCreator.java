package ar.edu.itba.pod.census.client.query;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

/**
 * Enum holding values that represent all the {@link HazelcastQuery} supported by the system.
 */
public enum HazelcastQueryCreator {
    QUERY_1(1) {
        @Override
        public HazelcastQuery createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query1(hazelcastInstance);
        }
    };

    /**
     * The query id this enum value represents.
     */
    private final int queryId;

    /**
     * Constructor.
     *
     * @param queryId The query id this enum value represents.
     */
    HazelcastQueryCreator(int queryId) {
        this.queryId = queryId;
    }

    /**
     * @return The query id this enum value represents.
     */
    public int getQueryId() {
        return queryId;
    }

    /**
     * Creates the {@link HazelcastQuery} the this enum value represents.
     *
     * @param hazelcastInstance The {@link HazelcastInstance} from which the {@link Job} is constructed.
     * @return The {@link HazelcastQuery} corresponding to the enum value.
     */
    public abstract HazelcastQuery createHazelcastQuery(HazelcastInstance hazelcastInstance);


    // ================================================================
    // Static stuff mainly used for retrieval of the enum values.
    // ================================================================

    /**
     * Stores each of this enum values into an array for fast retrieval by query id.
     */
    private static final HazelcastQueryCreator[] valuesByQueryId = {
            QUERY_1,
    };

    /**
     * Returns one enum value of this enum according to the given {@code queryId}.
     *
     * @param queryId The id of the query the returned enum value represents.
     * @return The value the given {@code queryId} represents.
     */
    public static HazelcastQueryCreator getCreatorByQueryId(int queryId) {
        return valuesByQueryId[queryId - 1]; // Query ids are 1-indexed, but the array is 0-indexed.
    }
}
