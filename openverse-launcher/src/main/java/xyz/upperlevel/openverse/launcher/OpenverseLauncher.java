package xyz.upperlevel.openverse.launcher;

import lombok.Getter;
import xyz.upperlevel.openverse.launcher.game.singleplayer.SingleplayerScene;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.GameSettings;

@Getter
public class OpenverseLauncher {
    private static OpenverseLauncher instance;
    private final Game game;

    public OpenverseLauncher() {
        instance = this;
        game = new Game(new GameSettings()
                .width(500)
                .height(500)
                .title("Openverse")
                .fullscreen(false)
                .createWindow()
        );
    }

    public void launch() {
        game.getStage().setScene(new SingleplayerScene(this));
        game.start();
    }

    public static OpenverseLauncher get() {
        return instance;
    }

    public static void main(String[] args) {
        new OpenverseLauncher().launch();
    }
}
