package xyz.upperlevel.openverse.world.chunk.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@RequiredArgsConstructor
public class ChunkDeleteEvent {

    @Getter
    private final Chunk chunk;
}
