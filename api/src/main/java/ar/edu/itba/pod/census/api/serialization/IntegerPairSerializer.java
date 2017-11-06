package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.models.Citizen;
import ar.edu.itba.pod.census.api.util.IntegerPair;
import ar.edu.itba.pod.census.api.util.Pair;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Citizen}.
 */
public class IntegerPairSerializer implements StreamSerializer<IntegerPair> {
    @Override
    public void write(ObjectDataOutput out, IntegerPair pair) throws IOException {
        out.writeInt(pair.getLeft());
        out.writeInt(pair.getRight());
    }

    @Override
    public IntegerPair read(ObjectDataInput in) throws IOException {
        final Integer left = in.readInt();
        final Integer right = in.readInt();

        return new IntegerPair(left, right);
    }

    @Override
    public int getTypeId() {
        return 3;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
