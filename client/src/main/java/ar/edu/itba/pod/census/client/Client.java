package ar.edu.itba.pod.census.client;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.models.Region;
import ar.edu.itba.pod.census.api.serialization.CitizenSerializer;
import ar.edu.itba.pod.census.client.io.cli.InputParams;
import ar.edu.itba.pod.census.client.io.input.StreamsCensusReader;
import ar.edu.itba.pod.census.client.query.Query1;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Client {
    private final static Logger LOGGER = LoggerFactory.getLogger(Client.class);

    /**
     * Name to be used for the Hazelcast cluster and maps.
     */
    private final static String NAME = "52056-55027-55431-55564";

    /**
     * Fake password for the Hazelcast group.
     */
    private final static String FAKE_PASSWORD = "password";

    public static void main(String[] args) throws IOException {
        LOGGER.info("Census client starting ...");

        // Get program arguments
        LOGGER.info("Reading program arguments");
        final InputParams inputParams = new InputParams();
        LOGGER.info("Finished reading program arguments");
        LOGGER.debug("Program arguments are: {}", inputParams);

        // Create the already configured hazelcast instance
        LOGGER.info("Creating Hazelcast instance");
        final HazelcastInstance hazelcastClient = createHazelcastClient(inputParams.getAddresses(), NAME, FAKE_PASSWORD);
        LOGGER.info("Finished creating Hazelcast instance");

        // Read data once Hazelcast instance is created
        LOGGER.info("Reading data file");
        final List<Citizen> citizens = StreamsCensusReader.readCitizens(inputParams.getDataFilePath());
        LOGGER.info("Finished reading data file");

        final Map<Region, Long> result = new Query1(hazelcastClient).perform(citizens);

        System.out.println("With hazelcast...");
        result.forEach((region, count) -> System.out.println(region + ": " + count));

        // Compare results with Java8 streams... TODO: remove this
        Map<Region, Long> java8Result = citizens.stream().map(Citizen::getRegion)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("With Java8...");
        java8Result.forEach((region, count) -> System.out.println(region + ": " + count));

        // TODO: get query from a repository or enum
    }

    /**
     * Creates and configures a {@link HazelcastInstance} to be used as a client.
     *
     * @return The created and configured {@link HazelcastInstance}.
     */
    private static HazelcastInstance createHazelcastClient(String[] addresses, String groupName, String groupPassword) {

        // ================================
        // Serialization config
        // ================================
        final SerializationConfig serializationConfig = new SerializationConfig()
                .addSerializerConfig(new SerializerConfig()
                        .setImplementation(new CitizenSerializer())
                        .setTypeClass(Citizen.class));

        // ================================
        // Networking config
        // ================================
        final ClientNetworkConfig networkingConfig = new ClientNetworkConfig()
                .addAddress(addresses);

        // ================================
        // Group config
        // ================================
        final GroupConfig groupConfig = new GroupConfig()
                .setName(groupName)
                .setPassword(groupPassword);

        // ================================
        // Instance config
        // ================================
        final ClientConfig config = new ClientConfig()
                .setSerializationConfig(serializationConfig)
                .setNetworkConfig(networkingConfig)
                .setGroupConfig(groupConfig);

        // Create the Hazelcast Client using the specified configuration
        return HazelcastClient.newHazelcastClient(config);
    }
}
