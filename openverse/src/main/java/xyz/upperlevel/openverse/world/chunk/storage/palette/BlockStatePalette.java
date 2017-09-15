package xyz.upperlevel.openverse.world.chunk.storage.palette;

import xyz.upperlevel.openverse.world.block.state.BlockState;

public interface BlockStatePalette {
    /**
     * Returns how many bits are used to represent the id of the BlockState
     * @return the bits used to represent the BlockState
     */
    int getIdLength();

    /**
     * Translates the BlockState into an id, and returns it
     * <p>Returns -1 if an overflow happens and the palette is unable to register the state
     * @param state the BlockState to translate
     * @return the id of the new BlockState or -1
     */
    int toId(BlockState state);

    /**
     * Returns the BlockState registered with this id or null if none is found
     * @param id the id to search
     * @return the BlockState associated with the id or null
     */
    BlockState toState(int id);

    interface OverflowHandler {
        int handle(int bits, BlockState overflowed);
    }
}
