package xyz.upperlevel.openverse.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Block;

@RequiredArgsConstructor
public class BlockUpdateEvent implements Event {
    @Getter
    private final World world;
    @Getter
    private final int x, y, z;
    @Getter
    private final BlockState oldState, state;
    private Block block = null;//Lazy initialization

    public Block getBlock() {
        if (block == null) {
            block = world.getBlock(x, y, z);
        }
        return block;
    }
}
