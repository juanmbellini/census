package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 7
 * (i.e returns a {@link Combiner} that holds department names of the same {@link Province}).
 */
public class Query7CombinerFactory implements CombinerFactory<Province, String, StringSet> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<String, StringSet> newCombiner(final Province province) {

        return new Combiner<String, StringSet>() {

            private final StringSet departments = new StringSet();

            @Override
            public void combine(String department) {

                departments.add(department);
            }

            @Override
            public StringSet finalizeChunk() {
                return departments;
            }
        };
    }
}