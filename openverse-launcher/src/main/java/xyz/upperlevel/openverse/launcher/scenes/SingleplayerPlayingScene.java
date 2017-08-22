package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

// this scene handles world rendering
@Getter
public class SingleplayerPlayingScene extends Stage {
    private final OpenverseLauncher launcher;
    private final SingleplayerScene parent;

    public SingleplayerPlayingScene(@NonNull SingleplayerScene parent) {
        this.launcher = parent.getLauncher();
        this.parent = parent;
    }

    @Override
    public void onEnable(Scene previous) {

    }

    @Override
    public void onRender() {
        parent.getClient().render();
    }
}