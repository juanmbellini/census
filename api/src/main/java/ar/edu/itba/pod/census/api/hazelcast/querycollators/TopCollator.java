package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hazelcast.mapreduce.Collator;

public class TopCollator<T, S> implements Collator<Map.Entry<T, Double>, List<S>> {
	private int n;

  public TopCollator(int n) {
      this.n = n;
  }

  @Override
  public List<S> collate(Iterable <Map.Entry<T, Double>> iterable) {
      List<S> list = new LinkedList<>();

      return list
          .stream()
          .sorted(Collections.reverseOrder())
          .limit(n)
          .collect(Collectors.toList());
  }
}
