package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.ulge.game.Scene;

@Getter
@RequiredArgsConstructor
public class ResourceScene implements Scene {
    private final ClientScene parent;

    @Override
    public void onEnable(Scene previous) {
        Openverse.resources().setup();
        Openverse.resources().load();
        parent.setScene(new LoginScene(parent));
    }

    @Override
    public void onDisable(Scene scene) {
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onFps() {
    }

    @Override
    public void onRender() {
    }
}
