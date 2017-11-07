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

public class TopWithMinNCollator<T> implements Collator<Entry<T, Long>, Map<T, Long>> {
	
	/**
   * The {@link Logger}.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(TopWithMinNCollator.class);
	
	private Integer n;
	
	public TopWithMinNCollator(final Integer n) {
		this.n = n;
	}

	@Override
	public Map<T, Long> collate(Iterable<Entry<T, Long>> arg0) {
		
		LOGGER.info("Running collator");
		
		final Map<T, Long> m = new HashMap<>();
		
		for (Entry<T, Long> e : arg0) {
			LOGGER.trace(e.getKey() + ": " + e.getValue());
			m.put(e.getKey(), e.getValue());
		}
		
		LOGGER.debug("Input: {}", m);
		
		final Map<T, Long> result = m.entrySet().stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
        .filter(tLongEntry -> tLongEntry.getValue() >= this.n)
        .collect(
        		Collectors.toMap(Entry::getKey, Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new)
        );
		
		LOGGER.debug("Result: {}", result);
		
		return result;
	}

}
