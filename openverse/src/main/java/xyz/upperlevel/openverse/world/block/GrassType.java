package xyz.upperlevel.openverse.world.block;

import xyz.upperlevel.openverse.world.block.property.BoolProperty;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;

public class GrassType extends BlockType {
    public static final BoolProperty DRY = new BoolProperty("dry");

    public GrassType() {
        super("grass");
        opaque = true;
        setDefaultState(getDefaultBlockState().with(DRY, true));
    }

    @Override
    public BlockStateRegistry createBlockState() {
        return BlockStateRegistry.of(this, DRY);
    }
}
