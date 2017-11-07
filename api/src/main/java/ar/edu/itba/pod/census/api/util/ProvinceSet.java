package ar.edu.itba.pod.census.api.util;

import ar.edu.itba.pod.census.api.models.Province;

import java.util.HashSet;

/**
 * A concrete extension of {@link HashSet} for {@link Province}.
 */
public class ProvinceSet extends HashSet<Province> {

    /**
     * Default constructor.
     */
    public ProvinceSet() {

    }

    /**
     * Constructor from another set.
     *
     * @param fromSet Another set of this type from which data will be added.
     */
    public ProvinceSet(ProvinceSet fromSet) {
        super(fromSet);
    }
}
