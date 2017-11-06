package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.Map;

/**
 * {@link Collator} class that orders by key in an {@link java.util.Map.Entry}
 * using the natural order of the keys.
 */
public class OrderByKeyCollator<K extends Comparable<? super K>, V> extends OrderByCollator<K, V>
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
    protected Comparator<Map.Entry<K, V>> getComparator() {
        return Map.Entry.comparingByKey(ascOrDescComparator);
    }
}
