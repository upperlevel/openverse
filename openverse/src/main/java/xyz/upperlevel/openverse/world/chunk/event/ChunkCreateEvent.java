package xyz.upperlevel.openverse.world.chunk.event;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Data
public class ChunkCreateEvent implements Event {

    private final Chunk chunk;
}
