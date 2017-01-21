package xyz.upperlevel.opencraft.world.block.shape;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BlockFaceData {

    public static final BlockFaceData NULL = new BlockFaceData("_null");

    @Getter
    @NonNull
    public final String id;

    public void load(Map<String, Object> data) {
    }

    public Map<String, Object> save() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        return data;
    }

    public BlockFaceData copy() {
        return new BlockFaceData(id); // todo
    }
}