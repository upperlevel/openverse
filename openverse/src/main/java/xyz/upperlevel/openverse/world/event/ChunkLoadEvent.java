package xyz.upperlevel.openverse.world.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@RequiredArgsConstructor
public class ChunkLoadEvent implements Event {
    private final Chunk chunk;
}
