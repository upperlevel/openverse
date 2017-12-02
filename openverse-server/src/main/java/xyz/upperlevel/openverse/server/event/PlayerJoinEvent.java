package xyz.upperlevel.openverse.server.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.server.world.ServerPlayer;

@RequiredArgsConstructor
public class PlayerJoinEvent implements Event {
    @Getter
    private final ServerPlayer player;
}
