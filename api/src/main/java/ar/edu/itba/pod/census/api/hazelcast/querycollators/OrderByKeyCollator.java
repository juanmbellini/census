package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * {@link Collator} class that orders by key in an {@link java.util.Map.Entry}.
 */
public final class OrderByKeyCollator<K extends Comparable<? super K>, V>
        implements Collator<Map.Entry<K, V>, Map<K, V>> {

    /**
     * Final comparator that dictates whether the sorting must be done ascending or descending.
     */
    private final Comparator<K> ascOrDescComparator;

    /**
     * Constructor.
     *
     * @param sortDirection Dictates whether the sorting must be done ascending or descending.
     */
    public OrderByKeyCollator(SortDirection sortDirection) {
        ascOrDescComparator = sortDirection.getSortDirectionComparator();
    }

    @Override
    public Map<K, V> collate(Iterable<Map.Entry<K, V>> entries) {
        return StreamSupport.stream(entries.spliterator(), false)
                .sorted(Map.Entry.comparingByKey(ascOrDescComparator))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (m1, m2) -> m1, LinkedHashMap::new));
    }
}
