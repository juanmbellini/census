package ar.edu.itba.pod.census.server;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        LOGGER.info("census Server Starting ...");
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Long, String> map = hazelcastInstance.getMap("data");
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator("newid");
        for (int i = 0; i < 100000; i++) {
            map.put(idGenerator.newId(), "message" + 1);
        }
    }
}
