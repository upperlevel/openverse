package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.resource.Registry;
import xyz.upperlevel.openverse.world.block.state.BlockState;

import java.util.Arrays;
import java.util.stream.Stream;

import static xyz.upperlevel.openverse.world.block.BlockType.AIR;

@Getter
public class BlockTypeRegistry extends Registry<BlockType> {
    private int nextId = 0;
    private DynamicArray<BlockType> idRegistry = new DynamicArray<>(256);

    public BlockTypeRegistry() {
        register(AIR);
        register(new GrassType());
        register(new BlockType("dirt"));
    }

    public void register(BlockType type) {
        register(type.getId(), type);
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

    public void registerId(BlockType type) {
        idRegistry.set(nextId, type);
        type.setRawId(nextId);
        nextId++;
    }

    @SuppressWarnings("unchecked")
    public Stream<BlockType> getOrderedEntries() {
        return (Stream)Arrays.stream(idRegistry.getArray(), 0, nextId);
    }
}
