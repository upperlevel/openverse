package xyz.upperlevel.openverse.launcher;

import xyz.upperlevel.openverse.launcher.scenes.SingleplayerUniverseScene;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.GameSettings;

public class OpenverseLauncher {

    private static OpenverseLauncher instance;

    private final Game game;

    public OpenverseLauncher() {
        game = new Game(new GameSettings());
    }

    public static Game getGame() {
        return instance.game;
    }

    public static void main(String[] args) {
        instance =  new OpenverseLauncher();

        instance.game.start();
        instance.game.setStage(new SingleplayerUniverseScene());
    }
}
