package ar.edu.itba.pod.census.models;

/**
 * Enum containing the regions in which the census is divided into.
 */
public enum Region {

    BUENOS_AIRES("Región Buenos Aires"),
    CENTER("Región Centro"),
    BIG_NORTH("Región del Norte Grande Argentino"),
    CUYO("Región del Nuevo Cuyo"),
    PATAGONIC("Región Patagónica");

    /**
     * The name given to the region.
     */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The name given to the region.
     */
    Region(String name) {
        this.name = name;
    }

    /**
     * @return The name given to the region.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param code The value of a region
     * @return The instance of the region corresponding to the value
     */
    public static Region getByVal(String code) throws IllegalArgumentException {
        for (Region region : Region.values()){
            if(code.equals(region.name)) return region;
        }

        throw new IllegalArgumentException();
    }
}
