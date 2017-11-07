package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.ProvinceSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 6
 * (i.e returns a {@link Combiner} that holds {@link Province}s of the same department name).
 */
public class Query6CombinerFactory implements CombinerFactory<String, Province, ProvinceSet> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Province, ProvinceSet> newCombiner(final String departmentName) {
        return new Combiner<Province, ProvinceSet>() {

            /**
             * Contains the {@link Province}s for the actual chunk.
             */
            private final ProvinceSet provinces = new ProvinceSet();

            @Override
            public void combine(Province province) {
                provinces.add(province);
            }

            @Override
            public ProvinceSet finalizeChunk() {
                // Send it in a new instance as the one held bu this Combiner will be reset.
                return new ProvinceSet(provinces);
            }

            @Override
            public void reset() {
                provinces.clear();
            }
        };
    }
}