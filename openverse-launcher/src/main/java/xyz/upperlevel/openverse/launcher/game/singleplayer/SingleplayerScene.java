package xyz.upperlevel.openverse.launcher.game.singleplayer;

import lombok.Getter;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.hermes.server.impl.direct.DirectServerConnection;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.launcher.loaders.ClientLoader;
import xyz.upperlevel.openverse.launcher.loaders.ClientWrapper;
import xyz.upperlevel.openverse.launcher.loaders.ServerLoader;
import xyz.upperlevel.openverse.launcher.loaders.ServerWrapper;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

@Getter
public class SingleplayerScene extends Stage {
    private final OpenverseLauncher launcher;

    private final ClientWrapper clientWrp;
    private final DirectClient client;
    private final DirectClientConnection clientConn;

    private final ServerWrapper serverWrp;
    private final DirectServer server;

    public SingleplayerScene(OpenverseLauncher launcher) {
        this.launcher = launcher;

        System.out.println("[Launcher] Creating client...");
        client = new DirectClient();
        clientConn = client.getConnection();
        clientConn.setCopy(true);
        ClientLoader clientLoader = new ClientLoader();
        clientLoader.load();

        System.out.println("[Launcher] Creating server...");
        server = new DirectServer();
        ServerLoader serverLoader = new ServerLoader();
        serverLoader.load();

        this.clientWrp = clientLoader.createClient(client);
        this.serverWrp = serverLoader.createServer(server);
    }

    @Override
    public void onEnable(Scene previous) {
        System.out.println("[Launcher] Joining client!");
        clientWrp.join(this);

        System.out.println("[Launcher] Joining server!");
        serverWrp.join();

        System.out.println("[Launcher] Setting up connection between those.");
        DirectServerConnection serverConnection = server.newConnection(clientConn);
        serverConnection.setCopy(true);
        clientConn.setOther(serverConnection);
    }

    @Override
    public void onDisable(Scene next) {
        super.onDisable(next);
        serverWrp.close();
    }
}
