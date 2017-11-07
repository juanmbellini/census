package ar.edu.itba.pod.census.api.hazelcast.querycombiners;

import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.ProvinceSet;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

/**
 * {@link CombinerFactory} for the query 5
 * (i.e returns a {@link Combiner} that count elements of the same department name).
 */
public class Query6CombinerFactory implements CombinerFactory<String, Province, ProvinceSet> {

    /**
     * Used for serialization of this {@link CombinerFactory}.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Combiner<Province, ProvinceSet> newCombiner(final String departmentName) {
        return new Combiner<Province, ProvinceSet>() {

            private final ProvinceSet provinces = new ProvinceSet();

            @Override
            public void combine(Province province) {
                provinces.add(province);
            }

            @Override
            public ProvinceSet finalizeChunk() {
                return provinces;
            }

            @Override
            public void reset() {
                // Do nothing as we need to maintain internal state
            }

            @Override
            public void finalizeCombine() {
                provinces.clear();
            }
        };
    }
}