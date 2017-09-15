package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.resource.Registry;
import xyz.upperlevel.openverse.world.block.state.BlockState;

import static xyz.upperlevel.openverse.world.block.BlockType.AIR;

@Getter
public class BlockTypeRegistry extends Registry<BlockType> {
    private int nextId = 1;
    private DynamicArray<BlockType> idRegistry = new DynamicArray<>(256);

    public BlockTypeRegistry() {
        register(AIR.getId(), AIR);
    }

    public BlockType entry(int id) {
        return idRegistry.get(id);
    }

    public BlockState getState(int id) {
        return entry(id & 0x0FFFFFFF).getBlockState(id >> (Integer.BYTES * 8 - 4));
    }

    public int getId(BlockState state) {
        return  state.getBlockType().getFullId(state);
    }
}
