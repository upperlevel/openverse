package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.hermes.server.impl.direct.DirectServerConnection;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

/**
 * This setScene that handles client connection to a singleplayer universe.
 * It has a client and a server that are connected each other.
 */
public class SingleplayerUniverseScene extends Stage {

    @Getter
    private final OpenverseClient client;

    @Getter
    private final OpenverseServer server;

    public SingleplayerUniverseScene() {
        DirectClient client = new DirectClient();
        DirectClientConnection clientConnection = client.getConnection();
        clientConnection.setCopy(true);
        this.client = new OpenverseClient(client);

        DirectServer server = new DirectServer();
        DirectServerConnection serverConnection = server.newConnection(clientConnection);
        serverConnection.setCopy(true);
        serverConnection.setOther(clientConnection);
        this.server = new OpenverseServer(server);
    }

    @Override
    public void onEnable(Scene previous) {
        Scene scene = new SingleplayerResourceLoadingScene(this);
        setScene(scene);
        scene.onEnable(null);
    }
}
