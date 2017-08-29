package xyz.upperlevel.openverse.world.chunk.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@RequiredArgsConstructor
public class ChunkDeleteEvent {
    private final Chunk chunk;
}
