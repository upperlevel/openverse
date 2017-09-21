package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.storage.palette.ArrayStatePalette;
import xyz.upperlevel.openverse.world.chunk.storage.palette.BlockStatePalette;
import xyz.upperlevel.openverse.world.chunk.storage.palette.HashStatePalette;
import xyz.upperlevel.openverse.world.chunk.storage.palette.RegistryStatePalette;

public class SimpleBlockStateStorage implements BlockStateStorage {
    public static final BlockState AIR_STATE = BlockType.AIR.getDefaultState();

    private BlockStatePalette palette;
    private VariableBitArray storage;
    private int bitsPerPalette;

    public SimpleBlockStateStorage() {
        setBitsPerPalette(4);
    }


    @Override
    public BlockState get(int x, int y, int z) {
        return get(index(x, y, z));
    }

    protected BlockState get(int index) {
        return palette.toState(storage.get(index));
    }

    @Override
    public void set(int x, int y, int z, BlockState state) {
        set(index(x, y, z), state);
    }

    protected void set(int index, BlockState state) {
        storage.set(index, palette.toId(state != null ? state : AIR_STATE));
    }

    public void setBitsPerPalette(int bits) {
        if (bits <= 4) {
            palette = new ArrayStatePalette(bitsPerPalette, this::onOverflow);
        } else if (bits <= 8) {
            palette = new HashStatePalette(bitsPerPalette, this::onOverflow);
        } else {
            palette = RegistryStatePalette.INSTANCE;
        }
        palette.toId(AIR_STATE);
        this.bitsPerPalette = palette.getIdLength();
        this.storage = new VariableBitArray(bitsPerPalette, 16*16*16);
    }

    private int onOverflow(int bitsNeeded, BlockState overflowed) {
        VariableBitArray oldIds = this.storage;
        BlockStatePalette oldPalette = this.palette;
        setBitsPerPalette(bitsNeeded);

        for (int i = 0; i < oldIds.capacity(); i++) {
            BlockState state = oldPalette.toState(oldIds.get(i));
            if (state != null) {
                set(i, state);
            }
        }

        return palette.toId(overflowed);
    }

    private static int index(int x, int y, int z) {
        //NOTE: Y Z X (cache-friendly)
        return y << 8 | z << 4 | x;
    }
}
