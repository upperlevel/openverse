package xyz.upperlevel.openverse.world.chunk.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@RequiredArgsConstructor
public class ChunkCreateEvent implements Event {
    private final Chunk chunk;
}
