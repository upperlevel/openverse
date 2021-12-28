package xyz.upperlevel.openverse.client.game;

import lombok.Getter;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

/**
 * This is the scene that the launcher searches for when call the client.
 * This may be the only class accessed by the launcher.
 */
public class ClientScene extends Stage {
    @Getter
    private final OpenverseClient client;

    public ClientScene(OpenverseClient client) {
        this.client = client;
    }

    @Override
    public void onEnable(Scene previous) {
        setScene(new ResourceScene(client, this));
    }
}
