package xyz.upperlevel.opencraft.world.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FaceData {

    public static final FaceData NULL = new FaceData("_null");

    public final String id;

    protected FaceData(String id) {
        Objects.requireNonNull(id, "Face data name cannot be null");
        this.id = id;
    }

    public final String getId() {
        return id;
    }

    public void load(Map<String, Object> data) {
    }

    public Map<String, Object> save() {
        return new HashMap<>();
    }

    public FaceData copy() {
        return new FaceData(id); // todo
    }
}