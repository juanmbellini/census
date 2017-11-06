package ar.edu.itba.pod.census.server;

import ar.edu.itba.pod.census.api.hazelcast.config.ConfigProvider;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Member node entry point class.
 */
public class Server {
    /**
     * The {@link Logger}
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    /**
     * Entry point for member node process.
     *
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        LOGGER.info("Starting member node...");
        startServer();
    }

    /**
     * Creates, configures and starts a {@link HazelcastInstance} to be used as a member node.
     */
    private static void startServer() {
        // Configuration
        final Config config = new Config()
                .setSerializationConfig(ConfigProvider.createSerializationConfig())
                .setGroupConfig(ConfigProvider.createGroupConfig());

        // Create the Hazelcast instance using the specified configuration
        Hazelcast.newHazelcastInstance(config);
    }
}
