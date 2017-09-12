package xyz.upperlevel.openverse.server.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.world.entity.Player;

@RequiredArgsConstructor
public class PlayerQuitEvent implements Event {
    @Getter
    private final ServerPlayer player;
}
