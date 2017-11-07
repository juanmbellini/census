package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * {@link Collator} class in charge of getting the top N elements.
 *
 * @param <T> The concrete type of element that will be processed by this collator.
 */
public class TopNCollator<T> implements Collator<Entry<T, Long>, Map<T, Long>> {

    /**
     * The number indicating the top.
     */
    private final Integer n;

    /**
     * Constructor.
     *
     * @param n The number indicating the top.
     */
    public TopNCollator(final Integer n) {
        this.n = n;
    }

    @Override
    public Map<T, Long> collate(Iterable<Entry<T, Long>> entries) {
        return StreamSupport.stream(entries.spliterator(), false)
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(this.n)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

}
