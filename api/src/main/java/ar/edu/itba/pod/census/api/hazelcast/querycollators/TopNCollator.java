package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.mapreduce.Collator;

public class TopNCollator<T> implements Collator<Entry<T, Long>, Map<T, Long>> {
	
	/**
   * The {@link Logger}.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(TopNCollator.class);
	
	private Integer n;
	
	public TopNCollator(final Integer n) {
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
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(this.n)
        .collect(
        		Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new)
        );
		
		LOGGER.debug("Result: {}", result);
		
		return result;
	}

}
