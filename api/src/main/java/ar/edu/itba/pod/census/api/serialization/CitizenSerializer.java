package ar.edu.itba.pod.census.api.serialization;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import ar.edu.itba.pod.census.api.models.Citizen;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link Citizen}.
 */
public class CitizenSerializer implements StreamSerializer<Citizen> {
    @Override
    public void write(ObjectDataOutput out, Citizen habitant) throws IOException {
        out.writeInt(habitant.getActivityConditionId());
        out.writeLong(habitant.getHomeId());
        out.writeUTF(habitant.getDepartmentName());
        out.writeUTF(habitant.getProvince().getName());
    }

    @Override
    public Citizen read(ObjectDataInput in) throws IOException {
        final int activityConditionId = in.readInt();
        final long homeId = in.readLong();
        final String departmentName = in.readUTF();
        final String provinceName = in.readUTF();

        return new Citizen(activityConditionId, homeId, departmentName, provinceName);
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
