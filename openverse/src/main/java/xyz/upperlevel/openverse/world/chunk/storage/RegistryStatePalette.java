package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.world.block.state.BlockState;

public class RegistryStatePalette implements BlockStatePalette {
    public static final RegistryStatePalette INSTANCE = new RegistryStatePalette();

    private final BlockTypeRegistry registry = Openverse.resources().blockTypes();

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
