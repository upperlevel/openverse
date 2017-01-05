package xyz.upperlevel.opencraft;

import lombok.Getter;
import xyz.upperlevel.graphicengine.Game;

public class OpenCraft extends Game {

    private static OpenCraft $;

    @Getter public final GameOptions options = new GameOptions();

    @Override
    public void start() {
        $ = this;
    }

    @Override
    public void loop() {
    }

    @Override
    public void close() {
    }

    public static OpenCraft $() {
        return $;
    }
}
