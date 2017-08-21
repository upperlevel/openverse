package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.hermes.server.impl.direct.DirectServerConnection;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.launcher.loaders.ClientLoader;
import xyz.upperlevel.openverse.launcher.loaders.ServerLoader;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

public class SingleplayerUniverseScene extends Stage {

    @Getter
    private final OpenverseClient client;

    @Getter
    private final OpenverseServer server;

    public SingleplayerUniverseScene() {
        System.out.println("START");
        DirectClient client = new DirectClient();
        DirectClientConnection clientConnection = client.getConnection();
        clientConnection.setCopy(true);
        ClientLoader clientLoader = new ClientLoader();
        clientLoader.load();
        this.client = clientLoader.createClient(client);

        System.out.println("MIDDLE");

        DirectServer server = new DirectServer();
        DirectServerConnection serverConnection = server.newConnection(clientConnection);
        serverConnection.setCopy(true);
        clientConnection.setOther(serverConnection);
        ServerLoader serverLoader = new ServerLoader();
        serverLoader.load();
        this.server = serverLoader.createServer(server);

        System.out.println("END");
    }

    @Override
    public void onEnable(Scene previous) {
        Scene scene = new SingleplayerResourceLoadingScene(this);
        setScene(scene);
        scene.onEnable(null);
    }
}
