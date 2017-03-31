package xyz.upperlevel.opencraft.resource.block;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockTypeManager {

    private Map<String, BlockType> blocks = new HashMap<>();

    public BlockTypeManager() {
    }

    public BlockTypeManager register(@NonNull BlockType type) {
        blocks.put(type.getId(), type);
        return this;
    }

    public BlockType get(String id) {
        return blocks.get(id);
    }

    public BlockTypeManager unregister(String id) {
        blocks.remove(id);
        return this;
    }

    public BlockTypeManager unregister(@NonNull BlockType type) {
        blocks.remove(type.getId());
        return this;
    }

    public Collection<BlockType> getAll() {
        return blocks.values();
    }
}
