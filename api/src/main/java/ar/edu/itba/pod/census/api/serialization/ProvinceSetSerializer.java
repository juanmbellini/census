package ar.edu.itba.pod.census.api.serialization;

import ar.edu.itba.pod.census.api.models.Province;
import ar.edu.itba.pod.census.api.util.ProvinceSet;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

/**
 * Custom serializer that is in charge of reading/writing {@link ProvinceSet}.
 */
public class ProvinceSetSerializer implements StreamSerializer<ProvinceSet> {
    @Override
    public void write(ObjectDataOutput out, ProvinceSet provinceSet) throws IOException {
        out.writeInt(provinceSet.size());
        for (Province province : provinceSet) {
            out.writeUTF(province.getName());
        }
    }

    @Override
    public ProvinceSet read(ObjectDataInput in) throws IOException {
        final ProvinceSet provinceSet = new ProvinceSet();
        final Integer count = in.readInt();
        for (int i = 0; i < count; i++) {
            provinceSet.add(Province.fromName(in.readUTF()));
        }
        return provinceSet;
    }

    @Override
    public int getTypeId() {
        return 8;
    }

    @Override
    public void destroy() {
        // Do nothing
    }
}
