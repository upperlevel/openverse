package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.state.BlockState;

public class ArrayStatePalette implements BlockStatePalette {
    private final int idLen;
    private int used = 0;
    private BlockState[] array;
    private OverflowHandler overflowHandler;

    public ArrayStatePalette(int bits, OverflowHandler overflowHandler) {
        this.idLen = bits;
        array = new BlockState[1 << bits];
        this.overflowHandler = overflowHandler;
    }


    @Override
    public int getIdLength() {
        return idLen;
    }

    @Override
    public int toId(BlockState state) {
        for (int i = 0; i < used; i++) {
            if (array[i] == state) {
                return i;
            }
        }
        if (used < array.length) {
            array[used] = state;
            return used++;
        } else {
            return overflowHandler.handle(idLen + 1, state);
        }
    }

    @Override
    public BlockState toState(int id) {
        return id >= 0 && id < array.length ? array[id] : null;
    }
}
