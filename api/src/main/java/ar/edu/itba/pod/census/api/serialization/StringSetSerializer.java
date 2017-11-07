package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.StringSet;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Citizen}.
 */
public class StringSetSerializer implements StreamSerializer<StringSet> {
    @Override
    public void write(ObjectDataOutput out, StringSet ss) throws IOException {
        out.writeInt(ss.size());
        for (String s : ss) {
            out.writeUTF(s);
        }
    }

    @Override
    public StringSet read(ObjectDataInput in) throws IOException {
        
        final StringSet s = new StringSet();
        
        final Integer count = in.readInt();
        
        for (int i = 0; i < count; i++) {
            s.add(in.readUTF());
        }

        return s;
    }

    @Override
    public int getTypeId() {
        return 4;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
