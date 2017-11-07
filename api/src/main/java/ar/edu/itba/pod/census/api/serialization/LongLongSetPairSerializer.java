package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.util.LongLongSetPair;
import ar.edu.itba.pod.census.api.util.LongSet;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link LongSet}.
 */
public class LongLongSetPairSerializer implements StreamSerializer<LongLongSetPair> {

    /**
     * {@link StreamSerializer} for a {@link LongSet}
     */
    private final LongSetSerializer longSetSerializer;

    public LongLongSetPairSerializer() {
        this.longSetSerializer = new LongSetSerializer();
    }

    @Override
    public void write(ObjectDataOutput out, LongLongSetPair pair) throws IOException {
        out.writeLong(pair.getLeft());
        longSetSerializer.write(out, pair.getRight());
    }

    @Override
    public LongLongSetPair read(ObjectDataInput in) throws IOException {
        final long left = in.readLong();
        final LongSet right = longSetSerializer.read(in);

        return new LongLongSetPair(left, right);
    }

    @Override
    public int getTypeId() {
        return 9;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
