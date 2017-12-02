package xyz.upperlevel.openverse.client.game;

import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

/**
 * This is the scene that the launcher searches for when call the client.
 * This may be the only class accessed by the launcher.
 */
public class ClientScene extends Stage {
    @Override
    public void onEnable(Scene previous) {
        setScene(new ResourceScene(this));
    }
}
