package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hazelcast.mapreduce.Collator;

public class TopNCollator<T> implements Collator<Entry<T, Long>, Map<T, Long>> {
	
	private Integer n;
	
	public TopNCollator(final Integer n) {
		this.n = n;
	}

	@Override
	public Map<T, Long> collate(Iterable<Entry<T, Long>> arg0) {
		
		final Map<T, Long> m = new HashMap<>();
		
		for (Entry<T, Long> e : arg0) {
			m.put(e.getKey(), e.getValue());
		}
		
		return m.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(this.n)
        .collect(
        		Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new)
        );
	}

}
