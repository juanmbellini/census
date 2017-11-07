package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.util.IntegerSet;
import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Citizen}.
 */
public class IntegerSetSerializer implements StreamSerializer<IntegerSet> {
    @Override
    public void write(ObjectDataOutput out, IntegerSet is) throws IOException {
        out.writeInt(is.size());
        for (Integer i : is) {
            out.writeInt(i);
        }
    }

    @Override
    public IntegerSet read(ObjectDataInput in) throws IOException {
        
        final IntegerSet is = new IntegerSet();
        
        final Integer count = in.readInt();
        
        for (int i = 0; i < count; i++) {
            is.add(in.readInt());
        }

        return is;
    }

    @Override
    public int getTypeId() {
        return 5;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
