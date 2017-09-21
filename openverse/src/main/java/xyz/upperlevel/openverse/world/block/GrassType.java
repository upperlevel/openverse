package xyz.upperlevel.openverse.world.block;

import xyz.upperlevel.openverse.world.block.property.BlockProperty;
import xyz.upperlevel.openverse.world.block.property.BoolProperty;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;

public class GrassType extends BlockType {
    public GrassType() {
        super("grass");
    }

    @Override
    public BlockStateRegistry createBlockState() {
        return BlockStateRegistry.of(this, new BoolProperty("dry"));
    }
}
