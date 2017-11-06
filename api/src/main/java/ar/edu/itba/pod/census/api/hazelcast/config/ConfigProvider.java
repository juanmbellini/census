package ar.edu.itba.pod.census.api.hazelcast.config;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.serialization.CitizenSerializer;
import ar.edu.itba.pod.census.api.serialization.BooleanPairSerializer;
import ar.edu.itba.pod.census.api.serialization.IntegerPairSerializer;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.Pair;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;

/**
 * Configuration class that provides different aspects of configuration to be reused by both client and member nodes.
 */
public class ConfigProvider {

    /**
     * Name to be used for the Hazelcast cluster and maps.
     */
    private final static String NAME = "52056-55027-55431-55564";

    /**
     * Fake password for the Hazelcast group.
     */
    private final static String FAKE_PASSWORD = "password";

    /**
     * @return The {@link GroupConfig} to be used by both client and member nodes.
     */
    public static GroupConfig createGroupConfig() {
        return new GroupConfig()
                .setName(NAME)
                .setPassword(FAKE_PASSWORD);
    }

    /**
     * @return The {@link SerializationConfig} to be used by both client and member nodes.
     */
    public static SerializationConfig createSerializationConfig() {
        return new SerializationConfig()
                .addSerializerConfig(new SerializerConfig()
                        .setImplementation(new CitizenSerializer())
                        .setTypeClass(Citizen.class))
                .addSerializerConfig(new SerializerConfig()
                        .setImplementation(new BooleanPairSerializer())
                        .setTypeClass(BooleanPair.class))
                .addSerializerConfig(new SerializerConfig()
                        .setImplementation(new IntegerPairSerializer())
                        .setTypeClass(IntegerPair.class));
    }
}
