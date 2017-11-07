package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.util.BooleanPair;
import ar.edu.itba.pod.census.api.util.StringPair;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Citizen}.
 */
public class StringPairSerializer implements StreamSerializer<StringPair> {
    @Override
    public void write(ObjectDataOutput out, StringPair pair) throws IOException {
        out.writeUTF(pair.getLeft());
        out.writeUTF(pair.getRight());
    }

    @Override
    public StringPair read(ObjectDataInput in) throws IOException {
        final String left = in.readUTF();
        final String right = in.readUTF();

        return new StringPair(left, right);
    }

    @Override
    public int getTypeId() {
        return 7;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
