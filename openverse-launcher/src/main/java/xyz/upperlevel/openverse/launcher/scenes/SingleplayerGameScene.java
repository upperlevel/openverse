package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

/**
 * This class handles singleplayer game state. It is used while a player
 * is playing in the world and handles guis.
 */
public class SingleplayerGameScene extends Stage {

    @Getter
    private final SingleplayerUniverseScene parent;

    public SingleplayerGameScene(@NonNull SingleplayerUniverseScene parent) {
        this.parent = parent;
    }

    @Override
    public void onRender() {
        super.onRender();

    }
}