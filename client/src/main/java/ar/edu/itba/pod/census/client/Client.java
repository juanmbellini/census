package ar.edu.itba.pod.census.client;

import ar.edu.itba.pod.census.api.hazelcast.config.ConfigProvider;
import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.client.io.cli.InputParams;
import ar.edu.itba.pod.census.client.io.input.StreamsCensusReader;
import ar.edu.itba.pod.census.client.io.output.OutputWriter;
import ar.edu.itba.pod.census.client.query.HazelcastQueryCreator;
import ar.edu.itba.pod.census.client.query.Query;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Client entry point class.
 */
public class Client {

    /**
     * The {@link Logger}
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Client.class);

    /**
     * Entry point for client process.
     *
     * @param args Program arguments.
     * @throws IOException If any IO error occurs during program execution.
     */
    public static void main(String[] args) throws IOException {
        final LocalDateTime startingClient = LocalDateTime.now();
        LOGGER.info("Census client starting ...");

        // Get program arguments
        LOGGER.info("Reading program arguments");
        final InputParams params = new InputParams();
        LOGGER.info("Finished reading program arguments");
        LOGGER.debug("Program arguments are: {}", params);

        // Create the already configured hazelcast instance
        LOGGER.info("Creating Hazelcast instance");
        final HazelcastInstance hazelcastClient = createHazelcastClient(params.getAddresses());
        LOGGER.info("Finished creating Hazelcast instance");

        // Read data once Hazelcast instance is created
        LOGGER.info("Reading data file");
        final LocalDateTime startingReading = LocalDateTime.now();
        final List<Citizen> citizens = StreamsCensusReader.readCitizens(params.getDataFilePath());
        final LocalDateTime finishedReading = LocalDateTime.now();
        LOGGER.info("Finished reading data file");

        LOGGER.info("Starting query {} task...", params.getQueryId());
        final Query.QueryParamsContainer queryParams = new Query.QueryParamsContainer(params.getN(), params.getProv());
        final LocalDateTime startingJob = LocalDateTime.now();
        final Map<?, ?> result = HazelcastQueryCreator.getCreatorByQueryId(params.getQueryId())
                .createHazelcastQuery(hazelcastClient)
                .perform(citizens, queryParams);
        final LocalDateTime finishedJob = LocalDateTime.now();
        LOGGER.info("Finished query task");

        // Save results (fallback into stdout if any IO error occurs)
        saveOutput(params.getOutputFilePath(), result,
                params.getTimestampsFilePath(), startingClient,
                startingReading, finishedReading, startingJob, finishedJob);


//        result.forEach((region, count) -> System.out.println(region + ": " + count));

        // Compare results with Java8 streams... TODO: remove this
//        Map<Region, Long> java8Result = citizens.stream().map(Citizen::getRegion)
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        System.out.println("With Java8...");
//        java8Result.forEach((region, count) -> System.out.println(region + ": " + count));
        LOGGER.info("Finished printing results");

    }

    /**
     * Creates and configures a {@link HazelcastInstance} to be used as a client.
     *
     * @param addresses Array containing the addresses of member nodes.
     * @return The created and configured {@link HazelcastInstance}.
     */
    private static HazelcastInstance createHazelcastClient(String[] addresses) {

        // Configuration
        final ClientConfig config = new ClientConfig()
                .setSerializationConfig(ConfigProvider.createSerializationConfig())
                .setGroupConfig(ConfigProvider.createGroupConfig())
                .setNetworkConfig(new ClientNetworkConfig().addAddress(addresses));

        // Create the Hazelcast Client using the specified configuration
        return HazelcastClient.newHazelcastClient(config);
    }

    /**
     * Saves the results.
     *
     * @param resultsOutputPath Path of file to where results must be saved into.
     * @param results           {@link Map} holding {@link Query} results.
     * @param timestampsPath    Path of file to where timestamps must be saved into.
     * @param startingClient    {@link LocalDateTime} representing the timestamp at which the client process started.
     * @param startingReading   {@link LocalDateTime} representing the timestamp
     *                          at which the reading data file procedure started.
     * @param finishedReading   {@link LocalDateTime} representing the timestamp
     *                          at which the reading data file procedure finished
     * @param startingJob       {@link LocalDateTime} representing the timestamp at which the map/reduce job started.
     * @param finishedJob       {@link LocalDateTime} representing the timestamp at which the map/reduce job finished.
     * @throws IOException If any IO error occurs while
     */
    private static void saveOutput(String resultsOutputPath, Map<?, ?> results,
                                   String timestampsPath, LocalDateTime startingClient,
                                   LocalDateTime startingReading, LocalDateTime finishedReading,
                                   LocalDateTime startingJob, LocalDateTime finishedJob) throws IOException {
        // Save results
        try {

            OutputWriter.saveResults(results, resultsOutputPath);
        } catch (IOException e) {
            LOGGER.debug("IOException while saving results output file. Exception message: {}", e.getMessage());
            LOGGER.trace("IOException stacktrace:", e);
            LOGGER.warn("Could not save results due to an IO error. Falling back to stdout");
            OutputWriter.printResults(results);
        }

        // Save timestamps
        final Map<LocalDateTime, String> timestamps = new TreeMap<>();
        timestamps.put(startingClient, "Client started");
        timestamps.put(startingReading, "Starting reading data file");
        timestamps.put(finishedReading, "Finished reading data file");
        timestamps.put(startingJob, "Starting map/reduce job");
        timestamps.put(finishedJob, "Finished map/reduce job");
        try {
            OutputWriter.saveTimestamps(timestamps, timestampsPath);
        } catch (IOException e) {
            LOGGER.debug("IOException while saving file. Exception message: {}", e.getMessage());
            LOGGER.trace("IOException stacktrace:", e);
            LOGGER.warn("Could not save timestamps due to an IO error. Falling back to stdout");
            OutputWriter.printTimestamps(timestamps);
        }
    }
}
