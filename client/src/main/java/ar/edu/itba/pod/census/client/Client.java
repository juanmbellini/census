package ar.edu.itba.pod.census.client;

import ar.edu.itba.pod.census.api.models.Citizen;
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
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


        // TODO: get query and perform it...


//        // Generar el tipo de mapa en base a la query y cargarlo
//        IMap<String, String> map = hazelcastClient.getMap("data");
//
//        LOGGER.info("Loading map");
//        //___________________________________
//        for (int i = 0; i < 1000; i++) {
//            LOGGER.info("Loading map {}", i);
//            map.put(String.valueOf(i), "Región Buenos Aires");
//        }
//        //___________________________________
//        LOGGER.info("Map loaded");
//
//        JobTracker tracker = hazelcastClient.getJobTracker("default");
//        LOGGER.info("Tracker loaded");
//
//        KeyValueSource<String, String> source = KeyValueSource.fromMap(map);
//        LOGGER.info("Source loaded");
//
//        Job<String, String> job = tracker.newJob(source);
//        LOGGER.info("Job generated");
//
//
//        try {
//            Map<String, Long> result = new Query1().execute(job);
//            LOGGER.info("Results obtained: {}", result.size());
//
//            for (Map.Entry<String, Long> entry : result.entrySet()) {
//                LOGGER.info("Query 1: " + entry.getKey() + " - " + entry.getValue());
//            }
//        } catch (Exception e) {
//            LOGGER.info("BOOOOOOOOOOOOOOOOOOOOOOOOOOM");
//        }
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
