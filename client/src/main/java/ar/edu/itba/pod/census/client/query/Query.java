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
     * @param citizens A {@link List} of {@link Citizen}s to query.
     * @return A {@link Map} holding the query result.
     */
    Map<K, V> perform(List<Citizen> citizens);
}
