package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.util.BooleanPair;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link BooleanPair}.
 */
public class BooleanPairSerializer implements StreamSerializer<BooleanPair> {
    @Override
    public void write(ObjectDataOutput out, BooleanPair pair) throws IOException {
        out.writeBoolean(pair.getLeft());
        out.writeBoolean(pair.getRight());
    }

    @Override
    public BooleanPair read(ObjectDataInput in) throws IOException {
        final Boolean left = in.readBoolean();
        final Boolean right = in.readBoolean();

        return new BooleanPair(left, right);
    }

    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
