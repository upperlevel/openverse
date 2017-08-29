package xyz.upperlevel.openverse.world.block.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.block.Block;

@RequiredArgsConstructor
public class BlockChangeEvent implements Event {

    @Getter
    private final BlockType oldType;

    @Getter
    private final Block block;

    public BlockType getNewType() {
        return block.getType();
    }
}
