package xyz.upperlevel.openverse.world.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.World;

@RequiredArgsConstructor
public class WorldCreateEvent implements Event {

    @Getter
    @NonNull
    private final World world;
}
