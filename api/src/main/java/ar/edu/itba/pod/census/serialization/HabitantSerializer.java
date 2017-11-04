package ar.edu.itba.pod.census.serialization;

import ar.edu.itba.pod.census.models.Habitant;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Habitant}.
 */
public class HabitantSerializer implements StreamSerializer<Habitant> {
    @Override
    public void write(ObjectDataOutput out, Habitant habitant) throws IOException {
        out.writeInt(habitant.getActivityConditionId());
        out.writeLong(habitant.getHomeId());
        out.writeUTF(habitant.getDepartmentName());
        out.writeUTF(habitant.getProvince().getName());
    }

    @Override
    public Habitant read(ObjectDataInput in) throws IOException {
        final int activityConditionId = in.readInt();
        final long homeId = in.readLong();
        final String departmentName = in.readUTF();
        final String provinceName = in.readUTF();

        return new Habitant(activityConditionId, homeId, departmentName, provinceName);
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
