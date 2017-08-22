package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.game.Scene;

@Getter
@RequiredArgsConstructor
public class GameScene implements Scene, Listener {
    private final OpenverseClient client;
    private final Player player;

    @Override
    public void onEnable(Scene previous) {
    }

    @Override
    public void onRender() {
    }
}
