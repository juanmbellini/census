package ar.edu.itba.pod.census.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Client {
    private final static Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        LOGGER.info("census Client Starting ...");
        ClientConfig config = new ClientConfig();
        GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");
        groupConfig.setPassword("dev-pass");
        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(config);

        IMap<Long, String> map = hazelcastClient.getMap("data");

        for (Map.Entry<Long, String> entry : map.entrySet()) {
            LOGGER.info(entry.getKey() + " - " + entry.getValue());
        }
    }
}
