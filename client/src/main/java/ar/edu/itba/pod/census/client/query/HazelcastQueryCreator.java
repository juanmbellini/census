package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.util.StringPair;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.mapreduce.Job;

/**
 * Enum holding values that represent all the {@link HazelcastQuery} supported by the system.
 */
public enum HazelcastQueryCreator {
    QUERY_1(1) {
        @Override
        public HazelcastQuery<Region, Long> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query1(hazelcastInstance);
        }
    },

    QUERY_2(2) {
        @Override
        public HazelcastQuery<String, Long> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query2(hazelcastInstance);
        }
    },

    QUERY_3(3) {
        @Override
        public HazelcastQuery<Region, String> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query3(hazelcastInstance);
        }
    },

    QUERY_4(4) {
        @Override
        public HazelcastQuery<Region, Long> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query4(hazelcastInstance);
        }
    },

    QUERY_5(5) {
        @Override
        public HazelcastQuery<Region, String> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query5(hazelcastInstance);
        }
    },

    QUERY_6(6) {
        @Override
        public HazelcastQuery<String, Long> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query6(hazelcastInstance);
        }
    },

    QUERY_7(7) {
        @Override
        public HazelcastQuery<String, Long> createHazelcastQuery(HazelcastInstance hazelcastInstance) {
            return new Query7(hazelcastInstance);
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
    public abstract HazelcastQuery<?, ?> createHazelcastQuery(HazelcastInstance hazelcastInstance);


    // ================================================================
    // Static stuff mainly used for retrieval of the enum values.
    // ================================================================

    /**
     * Stores each of this enum values into an array for fast retrieval by query id.
     */
    private static final HazelcastQueryCreator[] valuesByQueryId = {
            QUERY_1,
            QUERY_2,
            QUERY_3,
            QUERY_4,
            QUERY_5,
            QUERY_6,
            QUERY_7
    };

    /**
     * Returns one enum value of this enum according to the given {@code queryId}.
     *
     * @param queryId The id of the query the returned enum value represents.
     * @return The value the given {@code queryId} represents.
     */
    public static HazelcastQueryCreator getCreatorByQueryId(int queryId) {
        if (queryId < 1 || queryId > valuesByQueryId.length) {
            throw new IllegalArgumentException("Unknown query id");
        }
        return valuesByQueryId[queryId - 1]; // Query ids are 1-indexed, but the array is 0-indexed.
    }
}
