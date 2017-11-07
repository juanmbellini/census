package ar.edu.itba.pod.census.api.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Enum containing the Argentinean provinces.
 */
public enum Province {
    BUENOS_AIRES("Buenos Aires", Region.BUENOS_AIRES),
    CIUDAD_AUTONOMA_DE_BUENOS_AIRES("Ciudad Autónoma de Buenos Aires", Region.BUENOS_AIRES),
    CATAMARCA("Catamarca", Region.BIG_NORTH),
    CHACO("Chaco", Region.BIG_NORTH),
    CHUBUT("Chubut", Region.PATAGONIC),
    CORDOBA("Córdoba", Region.CENTER),
    CORRIENTES("Corrientes", Region.BIG_NORTH),
    ENTRE_RIOS("Entre Ríos", Region.CENTER),
    FORMOSA("Formosa", Region.BIG_NORTH),
    JUJUY("Jujuy", Region.BIG_NORTH),
    LA_PAMPA("La Pampa", Region.PATAGONIC),
    LA_RIOJA("La Rioja", Region.CUYO),
    MENDOZA("Mendoza", Region.CUYO),
    MISIONES("Misiones", Region.BIG_NORTH),
    NEUQUEN("Neuquén", Region.PATAGONIC),
    RIO_NEGRO("Río negro", Region.PATAGONIC),  // CSV does no have upper case for "Negro"
    SALTA("Salta", Region.BIG_NORTH),
    SAN_JUAN("San Juan", Region.CUYO),
    SAN_LUIS("San Luis", Region.CUYO),
    SANTA_CRUZ("Santa Cruz", Region.PATAGONIC),
    SANTA_FE("Santa Fe", Region.CENTER),
    SANTIAGO_DEL_ESTERO("Santiago del Estero", Region.BIG_NORTH),
    TIERRA_DEL_FUEGO("Tierra del Fuego", Region.PATAGONIC),
    TUCUMAN("Tucumán", Region.BIG_NORTH);


    /**
     * {@link Map} storing each province with their name as key, for fast retrieval of enum value by name.
     */
    private final static Map<String, Province> valuesByName = Collections
            .unmodifiableMap(Arrays.stream(Province.values())
                    .collect(Collectors.toMap(Province::getName, Function.identity())));

    /**
     * The province real name.
     */
    private final String name;

    /**
     * The {@link Region} to which the province belongs to.
     */
    private final Region region;


    /**
     * Constructor.
     *
     * @param name   The province real name.
     * @param region The {@link Region} to which the province belongs to.
     */
    Province(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    /**
     * @return The province real name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The {@link Region} to which the province belongs to.
     */
    public Region getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Finds the {@link Province} with the given name.
     *
     * @param name The province name.
     * @return The Province with the given name.
     */
    /* package */
    static Province fromName(String name) {
        return valuesByName.get(name);
    }
}