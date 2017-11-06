package ar.edu.itba.pod.census.api.hazelcast.querymappers;

import ar.edu.itba.pod.census.api.models.Region;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query2Mapper implements Mapper<String, String, String, Long> {
    private final static Logger LOGGER = LoggerFactory.getLogger(Query2Mapper.class);
    @Override
    public void map(String s1, String regionName, Context<String, Long> context) {
        try {

            Region key = Region.getByVal(regionName);

            context.emit(key.getName(), 1L);
        } catch (IllegalArgumentException e){
            LOGGER.info("BOOOOOOOOOOOM");
        }

    }
}
