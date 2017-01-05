package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.BlockData;

import java.util.LinkedList;
import java.util.List;

public class BlockRegistry {

    @Getter private final List<BlockData> blocksData = new LinkedList<BlockData>();

    public int getId(BlockData block) {
        return blocksData.indexOf(block);
    }

    public BlockData getBlockData(int id) {
        return blocksData.get(id);
    }

    public void register(BlockData block) {
        blocksData.add(block);
        block.setId(getId(block));
    }

    public boolean unregister(BlockData block) {
        return blocksData.remove(block);
    }
}
