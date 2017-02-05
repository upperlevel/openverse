package xyz.upperlevel.opencraft.common;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockProvider {

    @Getter
    private final Map<String, BlockType> blocks = new HashMap<>();

    public BlockProvider() {
    }

    public void registerBlock(BlockType block) {
        Objects.requireNonNull(block, "'block' cannot be null");
        blocks.put(block.getId(), block);
    }

    public boolean unregisterBlock(BlockType block) {
        Objects.requireNonNull(block, "'block' cannot be null");
        return blocks.remove(block.getId()) != null;
    }

    public BlockType getBlock(String id) {
        Objects.requireNonNull(id, "'id' cannot be null");
        return blocks.get(id);
    }
}
