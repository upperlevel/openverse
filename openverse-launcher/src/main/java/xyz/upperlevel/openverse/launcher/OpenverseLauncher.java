package xyz.upperlevel.openverse.launcher;

import lombok.Getter;
import xyz.upperlevel.openverse.launcher.scenes.SingleplayerScene;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.GameSettings;

@Getter
public class OpenverseLauncher {
    private final Game game;

    public OpenverseLauncher() {
        game = new Game(new GameSettings()
                .width(500)
                .height(500)
                .title("Openverse")
                .fullscreen(false)
        );
    }

    public void launch() {
        game.setStage(new SingleplayerScene());
        game.start();
    }

    public static void main(String[] args) {
        new OpenverseLauncher().launch();
    }
}
