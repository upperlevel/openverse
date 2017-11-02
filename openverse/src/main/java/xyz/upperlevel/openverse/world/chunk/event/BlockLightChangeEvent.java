package xyz.upperlevel.openverse.world.chunk.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.chunk.Block;

@Getter
@RequiredArgsConstructor
public class BlockLightChangeEvent implements Event {
    private final Block block;
    private final int oldLight, newLight;
}
