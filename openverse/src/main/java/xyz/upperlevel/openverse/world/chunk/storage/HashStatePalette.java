package xyz.upperlevel.openverse.world.chunk.storage;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import xyz.upperlevel.openverse.world.block.state.BlockState;

public class HashStatePalette implements BlockStatePalette {
    private final int idLen;
    private BiMap<BlockState, Integer> biMap;//TODO: don't use a BiMap but a more specific class
    private OverflowHandler overflowHandler;

    public HashStatePalette(int bits, OverflowHandler overflowHandler) {
        this.idLen = bits;
        biMap = HashBiMap.create(1 << bits);
        this.overflowHandler = overflowHandler;
    }


    @Override
    public int getIdLength() {
        return idLen;
    }

    @Override
    public int toId(BlockState state) {
        Integer res = biMap.get(state);
        if (res != null) {
            return res;
        }
        int index = biMap.size();
        if (index < (1 << idLen)) {
            biMap.put(state, index);
            return index;
        } else {
            return overflowHandler.handle(idLen + 1, state);
        }
    }

    @Override
    public BlockState toState(int id) {
        return biMap.inverse().get(id);
    }
}