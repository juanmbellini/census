package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * {@link Collator} class that orders by a given comparator of {@link Map.Entry}.
 */
public abstract class OrderByCollator<K, V> implements Collator<Map.Entry<K, V>, Map<K, V>> {

    @Override
    public final Map<K, V> collate(Iterable<Map.Entry<K, V>> entries) {
        return StreamSupport.stream(entries.spliterator(), false)
                .sorted(getComparator())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (m1, m2) -> m1, LinkedHashMap::new));
    }

    /**
     * Abstract method that must be overrode to tell how to sort {@link java.util.Map.Entry}.
     *
     * @return A {@link Comparator} of {@link java.util.Map.Entry} that will be used to sort entries
     * in the collate process.
     */
    protected abstract Comparator<Map.Entry<K, V>> getComparator();
}
