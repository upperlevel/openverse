package xyz.upperlevel.openverse.launcher;

import lombok.Getter;
import xyz.upperlevel.ulge.game.GamePresettings;

public final class OpenverseSystem {

    @Getter
    private static OpenverseHandler handler = new OpenverseHandler(new GamePresettings()
            .width(500)
            .height(500)
            .title("Openverse"));

    private OpenverseSystem() {
    }

    public static void launch() {
        handler.start();
    }

    public static void stop() {
        handler.stop();
    }

    public static OpenverseDirector getDirector() {
        return handler.getDirector();
    }
}
