package xyz.upperlevel.openverse.client.render.block;

import xyz.upperlevel.openverse.world.block.BlockType;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BlockTypeLocator {
    private Map<BlockType, Path>  paths = new HashMap<>();

    public void register(BlockType type, Path path) {
        paths.put(type, path);
    }
}
