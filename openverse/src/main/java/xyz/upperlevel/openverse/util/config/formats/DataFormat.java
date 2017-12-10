package xyz.upperlevel.openverse.util.config.formats;

import xyz.upperlevel.openverse.util.config.Config;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

public interface DataFormat {
    default Config toConfig(DataInput in) throws IOException {
        return Config.wrap(deserialize(in));
    }


    Map<String, Object> deserialize(DataInput in) throws IOException;

    void serialize(Map<String, Object> map, DataOutput out) throws IOException;
}
