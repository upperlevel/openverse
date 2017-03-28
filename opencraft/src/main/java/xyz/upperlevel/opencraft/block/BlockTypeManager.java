package xyz.upperlevel.opencraft.block;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockTypeManager {

    private Map<String, BlockType> blocks = new HashMap<>();

    public BlockTypeManager() {
    }

    public void add(@NonNull BlockType type) {
        blocks.put(type.getId(), type);
    }

    public BlockType get(BlockType type) {
        return type;
    }

    public Collection<BlockType> getAll() {
        return blocks.values();
    }
}
