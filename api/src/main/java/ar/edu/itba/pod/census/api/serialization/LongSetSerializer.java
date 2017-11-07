package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.util.IntegerSet;
import ar.edu.itba.pod.census.api.util.LongSet;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Citizen}.
 */
public class LongSetSerializer implements StreamSerializer<LongSet> {
    @Override
    public void write(ObjectDataOutput out, LongSet ls) throws IOException {
        out.writeInt(ls.size());
        for (Long i : ls) {
            out.writeLong(i);
        }
    }

    @Override
    public LongSet read(ObjectDataInput in) throws IOException {
        
        final LongSet ls = new LongSet();
        
        final Integer count = in.readInt();
        
        for (int i = 0; i < count; i++) {
            ls.add(in.readLong());
        }

        return ls;
    }

    @Override
    public int getTypeId() {
        return 6;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
