package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;
import xyz.upperlevel.openverse.world.block.tileentity.TileEntity;

@Getter
public class BlockType {
    private final String id;
    private int rawId; // todo

    protected final BlockStateRegistry blockState;
    private BlockState defaultBlockState;

    public BlockType(String id) {
        this.id = id;
        this.blockState = createBlockState();
        setDefaultState(blockState.getDefaultState());
    }


    public BlockStateRegistry createBlockState() {
        return BlockStateRegistry.of(this); //Creates a BlockStateRegistry with no propriety
    }

    /**
     * Creates {@link TileEntity} for this block. If none, returns {@code null}.
     */
    public TileEntity createTileEntity() {
        return null;
    }

    public BlockState getDefaultState() {
        return defaultBlockState;
    }

    public void setDefaultState(BlockState state) {
        this.defaultBlockState = state;
    }

    public BlockState getBlockState(int meta) {
        return blockState.getState(meta);
    }

    public int getStateMeta(BlockState state) {
        return state.getId();
    }

    public int getFullId(BlockState state) {
        return rawId | (state.getId() & 0xF);
    }
}