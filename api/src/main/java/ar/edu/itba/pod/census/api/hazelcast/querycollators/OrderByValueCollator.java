package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.Map;

/**
 * {@link Collator} class that orders by value in an {@link java.util.Map.Entry}
 * using the natural order of the values.
 */
public class OrderByValueCollator<K, V extends Comparable<? super V>> extends OrderByCollator<K, V>
        implements Collator<Map.Entry<K, V>, Map<K, V>> {

    /**
     * Final comparator that dictates whether the sorting must be done ascending or descending.
     */
    private final Comparator<V> ascOrDescComparator;

    /**
     * Constructor.
     *
     * @param sortDirection Dictates whether the sorting must be done ascending or descending.
     */
    public OrderByValueCollator(SortDirection sortDirection) {
        ascOrDescComparator = sortDirection.getSortDirectionComparator();
    }

    @Override
    protected Comparator<Map.Entry<K, V>> getComparator() {
        return Map.Entry.comparingByValue(ascOrDescComparator);
    }
}
