package xyz.upperlevel.openverse.launcher;

import lombok.Getter;
import xyz.upperlevel.ulge.game.Game;
import xyz.upperlevel.ulge.game.GamePresettings;

public class OpenverseHandler extends Game {

    @Getter
    private OpenverseDirector director = new OpenverseDirector();

    public OpenverseHandler(GamePresettings presettings) {
        super(presettings);
    }
}
