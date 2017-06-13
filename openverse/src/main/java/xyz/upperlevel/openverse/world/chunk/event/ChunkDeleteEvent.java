package xyz.upperlevel.openverse.world.chunk.event;

import lombok.Data;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Data
public class ChunkDeleteEvent {

    private final Chunk chunk;
}
