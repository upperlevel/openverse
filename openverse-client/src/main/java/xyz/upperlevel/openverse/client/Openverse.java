package xyz.upperlevel.openverse.client;

import lombok.Getter;
import xyz.upperlevel.openverse.client.resource.Resources;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.GamePresettings;

public final class Openverse {

    @Getter
    private Resources resources = new Resources();

    @Getter
    private Game game = new Game(GamePresettings.builder()
            .width(500)
            .height(500)
            .title("Openverse")
            .build()
    );

    @Getter
    private OpenverseDirector director = new OpenverseDirector();

    public Openverse() {
        game.getStage().stage(new OpenverseDirector());
    }

    private void launch() {
        game.start();
    }

    public void destroy() {
        game.stop();
    }

    public static final Openverse get = new Openverse();

    public static void main(String[] args) {
        get.launch();
    }

    public static Openverse get() {
        return get;
    }
}
