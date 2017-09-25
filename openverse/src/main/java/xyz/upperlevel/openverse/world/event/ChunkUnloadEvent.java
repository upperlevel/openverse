package xyz.upperlevel.openverse.world.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

@Getter
@RequiredArgsConstructor
public class ChunkUnloadEvent implements Event {
    private final Chunk chunk;
}
