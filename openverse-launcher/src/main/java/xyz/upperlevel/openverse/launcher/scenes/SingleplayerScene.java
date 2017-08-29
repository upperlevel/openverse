package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.hermes.server.impl.direct.DirectServerConnection;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.launcher.loaders.ClientLoader;
import xyz.upperlevel.openverse.launcher.loaders.ClientProxyWrapper;
import xyz.upperlevel.openverse.launcher.loaders.ProxyWrapper;
import xyz.upperlevel.openverse.launcher.loaders.ServerLoader;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

@Getter
public class SingleplayerScene extends Stage {
    private final OpenverseLauncher launcher;

    private final ClientProxyWrapper client;
    private final ProxyWrapper server;

    public SingleplayerScene(OpenverseLauncher launcher) {
        this.launcher = launcher;

        System.out.println("Creating client...");
        DirectClient client = new DirectClient();
        DirectClientConnection clientConnection = client.getConnection();
        clientConnection.setCopy(true);
        ClientLoader clientLoader = new ClientLoader();
        clientLoader.load();
        this.client = clientLoader.createClient(client);

        System.out.println("Creating server...");
        DirectServer server = new DirectServer();
        DirectServerConnection serverConnection = server.newConnection(clientConnection);
        serverConnection.setCopy(true);
        clientConnection.setOther(serverConnection);
        ServerLoader serverLoader = new ServerLoader();
        serverLoader.load();
        this.server = serverLoader.createServer(server);
    }

    @Override
    public void onEnable(Scene previous) {
        System.out.println("Enabling resource scene...");
        setScene(new SingleplayerResourceScene(this));
    }
}
