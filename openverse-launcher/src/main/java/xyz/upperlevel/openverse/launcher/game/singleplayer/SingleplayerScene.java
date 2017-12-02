package xyz.upperlevel.openverse.launcher.game.singleplayer;

import lombok.Getter;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.openverse.launcher.ConsoleListener;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.launcher.loaders.ClientLoader;
import xyz.upperlevel.openverse.launcher.loaders.ClientWrapper;
import xyz.upperlevel.openverse.launcher.loaders.ServerLoader;
import xyz.upperlevel.openverse.launcher.loaders.ServerWrapper;
import xyz.upperlevel.openverse.launcher.util.ConsoleOutputStream;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

@Getter
public class SingleplayerScene extends Stage {
    public static final boolean COPY = true;
    private final OpenverseLauncher launcher;

    private final ClientWrapper clientWrp;
    private final DirectClient client;
    private final DirectClientConnection clientConn;

    private final ServerWrapper serverWrp;
    private final DirectServer server;

    private ConsoleListener consoleListener;
    private Thread consoleThread;

    public SingleplayerScene(OpenverseLauncher launcher) {
        this.launcher = launcher;

        System.out.println("[Launcher] Creating client...");
        client = new DirectClient();
        clientConn = client.getConnection();
        clientConn.setCopy(COPY);
        ClientLoader clientLoader = new ClientLoader();
        clientLoader.load();

        System.out.println("[Launcher] Creating server...");
        server = new DirectServer();
        ServerLoader serverLoader = new ServerLoader();
        serverLoader.load();

        consoleListener = new ConsoleListener();
        PrintStream writer = new PrintStream(new ConsoleOutputStream(consoleListener.getIn()));

        this.clientWrp = clientLoader.createClient(client, writer);
        this.serverWrp = serverLoader.createServer(server, writer);

        consoleListener.setServer(serverWrp);
        consoleThread = new Thread(null, consoleListener::run, "Console thread");
    }

    @Override
    public void onEnable(Scene previous) {
        System.out.println("[Launcher] Joining server!");
        serverWrp.join();

        System.out.println("[Launcher] Joining client!");
        clientWrp.join(this);

        System.out.println("[Launcher] Setting up connection");
        server.newConnection(clientConn, COPY);
        consoleThread.start();
    }

    @Override
    public void onDisable(Scene next) {
        super.onDisable(next);
        serverWrp.close();
    }

    public Thread getThreadByName(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) return t;
        }
        return null;
    }
}
