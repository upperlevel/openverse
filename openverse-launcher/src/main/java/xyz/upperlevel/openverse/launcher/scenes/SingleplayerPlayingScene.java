package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

import static java.lang.System.out;

// this scene handles world rendering
public class SingleplayerPlayingScene extends Stage {

    @Getter
    private final SingleplayerScene parent;

    public SingleplayerPlayingScene(@NonNull SingleplayerScene parent) {
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