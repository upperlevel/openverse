package xyz.upperlevel.openverse.world.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.World;

@RequiredArgsConstructor
public class WorldDeleteEvent implements Event {

    @Getter
    private final World world;
}
