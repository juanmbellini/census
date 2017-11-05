package ar.edu.itba.pod.census.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.*;
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

        // Asi se setean todas las IP que vienen por parametro, se le puede mandar una lista/array/etc
        ClientNetworkConfig networkConfig = new ClientNetworkConfig();
        networkConfig.addAddress("127.0.0.1:5701");
        config.setNetworkConfig(networkConfig);


        HazelcastInstance hazelcastClient = HazelcastClient.newHazelcastClient(config);

        // ManagementCenterConfig manCenterCfg = new ManagementCenterConfig();
        // manCenterCfg.setEnabled(true).setUrl("http://localhost:8080/mancenter");

        IMap<Long, String> map = hazelcastClient.getMap("data");

        for (Map.Entry<Long, String> entry : map.entrySet()) {
            LOGGER.info(entry.getKey() + " - " + entry.getValue());
        }
    }
}
