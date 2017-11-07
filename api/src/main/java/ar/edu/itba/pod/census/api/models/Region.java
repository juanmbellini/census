package ar.edu.itba.pod.census.api.models;

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

    @Override
    public String toString() {
        return name;
    }
}
