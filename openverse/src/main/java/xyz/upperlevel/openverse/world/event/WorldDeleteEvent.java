package xyz.upperlevel.openverse.world.event;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.World;

@Data
public class WorldDeleteEvent implements Event {

    private final World world;
}
