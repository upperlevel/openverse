package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.resource.Registry;
import xyz.upperlevel.openverse.world.block.state.BlockState;

import java.util.Arrays;
import java.util.stream.Stream;

import static xyz.upperlevel.openverse.world.block.BlockType.AIR;

public class BlockTypeRegistry extends Registry<BlockType> {
    @Getter
    private final OpenverseProxy module;

    @Getter
    private int nextId = 0;

    @Getter
    private final DynamicArray<BlockType> idRegistry = new DynamicArray<>(256);

    public BlockTypeRegistry(OpenverseProxy module) {
        this.module = module;

        register(AIR);
        register(new GrassType());
        register(new BlockType("dirt", true));
        register(new BlockType("test", true));
        register(new PhotonBlockType());
    }

    public void register(BlockType type) {
        register(type.getId(), type);
    }

    public BlockType entry(int id) {
        return idRegistry.get(id);
    }

    public BlockType getAir() {
        return BlockType.AIR;
    }

    public BlockType getDirt() {
        return entry("dirt");
    }

    public BlockType getGrass() {
        return entry("grass");
    }

    public BlockState getState(int id) {
        return entry(id & 0x0FFFFFFF).getBlockState(id >> 28);
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
