package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

import static java.lang.System.out;

// this scene handles world rendering
public class SingleplayerGameScene extends Stage {

    @Getter
    private final SingleplayerUniverseScene parent;

    public SingleplayerGameScene(@NonNull SingleplayerUniverseScene parent) {
        this.parent = parent;
    }

    @Override
    public void onEnable(Scene previous) {
        out.println("Game scene has been started.");
    }

    @Override
    public void onRender() {
        // todo render player
    }
}