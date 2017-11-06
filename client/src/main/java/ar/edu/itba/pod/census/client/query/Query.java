package ar.edu.itba.pod.census.client.query;

import ar.edu.itba.pod.census.api.models.Citizen;

import java.util.List;
import java.util.Map;

/**
 * Defines behaviour for an object that represents a query.
 *
 * @param <K> The type of the key of the resulting map of the query.
 * @param <V> The type of the value of the resulting map of the query.
 */
public interface Query<K, V> {

    /**
     * Performs the query over the given {@code citizens}.
     *
     * @param citizens    A {@link List} of {@link Citizen}s to query.
     * @param queryParams The {@link QueryParamsContainer} holding query params that might be used by the query.
     * @return A {@link Map} holding the query result.
     */
    Map<K, V> perform(List<Citizen> citizens, QueryParamsContainer queryParams);

    /**
     * Container class that holds query params.
     */
    final static class QueryParamsContainer {

        /**
         * The 'n' query param.
         */
        private final Integer n;

        /**
         * The 'prov' query param.
         */
        private final String prov;

        /**
         * Constructor.
         *
         * @param n    The 'n' query param.
         * @param prov The 'prov' query param.
         */
        public QueryParamsContainer(Integer n, String prov) {
            this.n = n;
            this.prov = prov;
        }

        /**
         * @return The 'n' query param.
         */
        public Integer getN() {
            return n;
        }

        /**
         * @return The 'prov' query param.
         */
        public String getProv() {
            return prov;
        }
    }
}
