package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import com.hazelcast.mapreduce.Collator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * {@link Collator} class in charge of getting the top N elements.
 *
 * @param <T> The concrete type of element that will be processed by this collator.
 */
public class TopNCollator<T> implements Collator<Entry<T, Long>, Map<T, Long>> {

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(TopNCollator.class);

    /**
     * The number indicating the top.
     */
    private Integer n;

    /**
     * Constructor.
     *
     * @param n The number indicating the top.
     */
    public TopNCollator(final Integer n) {
        this.n = n;
    }

    @Override
    public Map<T, Long> collate(Iterable<Entry<T, Long>> arg0) {

        LOGGER.info("Running collator");

        final Map<T, Long> m = new HashMap<>();

        for (Entry<T, Long> e : arg0) {
            m.put(e.getKey(), e.getValue());
        }

        return m.entrySet().stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(this.n)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

}
