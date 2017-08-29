package xyz.upperlevel.openverse.client;

import lombok.Getter;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

/**
 * This is the scene that the launcher searches for when call the client.
 * This may be the only class accessed by the launcher.
 */
public class ClientScene extends Stage {
    @Override
    public void onEnable(Scene previous) {
        Openverse.logger().info("Welcome, you have just joined the OpenverseClient.");
        setScene(new ResourceScene(this));
    }
}
