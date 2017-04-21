package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockTypeManager {

    private Map<String, BlockType> types = new HashMap<>();

    public BlockTypeManager() {
    }

    public BlockTypeManager register(@NonNull BlockType type) {
        types.put(type.getId(), type);
        return this;
    }

    public BlockType get(String id) {
        return types.get(id);
    }

    public Collection<BlockType> get() {
        return types.values();
    }

    public void unregister(String id) {
        types.remove(id);
    }

    public void unregister(@NonNull BlockType type) {
        types.remove(type.getId());
    }
}
