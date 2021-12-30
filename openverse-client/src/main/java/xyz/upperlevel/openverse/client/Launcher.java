package xyz.upperlevel.openverse.client;

import lombok.Getter;
import xyz.upperlevel.openverse.client.launcher.SingleplayerScene;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.GameSettings;

import java.io.*;

public class Launcher {
    public static Launcher instance;

    @Getter
    private final Game game;

    protected Launcher() {
        Launcher.instance = this;

        this.game = new Game(new GameSettings()
                .width(1024)
                .height(720)
                .title("Openverse")
                .fullscreen(false)
                .createWindow()
        );
    }

    public void launch() {
        game.getStage().setScene(new SingleplayerScene());
        game.start();
    }

    public static Launcher get() {
        return instance;
    }

    public static void main(String[] args) {
        new Launcher().launch();
    }
}
