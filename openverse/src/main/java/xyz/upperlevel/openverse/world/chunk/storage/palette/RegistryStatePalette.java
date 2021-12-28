package xyz.upperlevel.openverse.world.chunk.storage.palette;

import lombok.Getter;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.world.block.state.BlockState;

public class RegistryStatePalette implements BlockStatePalette {
    @Getter
    private final BlockTypeRegistry registry;

    public RegistryStatePalette(BlockTypeRegistry registry) {
        this.registry = registry;
    }

    @Override
    public int getIdLength() {
        return Integer.BYTES * 8;
    }

    @Override
    public int toId(BlockState state) {
        return registry.getId(state);
    }

    @Override
    public BlockState toState(int id) {
        return registry.getState(id);
    }
}
