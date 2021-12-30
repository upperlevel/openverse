package xyz.upperlevel.openverse.client.launcher;

import lombok.Getter;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

import java.io.PrintStream;

/**
 * Important!
 * Do not hold any reference to core classes:
 * client and server will have their own class loaders so that the core package is initialized for both.
 */

public class SingleplayerScene extends Stage {
    private static final boolean CONNECTION_COPY = true;

    @Getter
    private OpenverseClient client;

    @Getter
    private OpenverseServer server;

    private DirectClient clientEndpoint;
    private DirectServer serverEndpoint;

    private ClassLoader clientClassLoader;
    private ClassLoader serverClassLoader;

    private final Thread consoleThread;


    public SingleplayerScene() {
        this.clientEndpoint = new DirectClient();
        clientEndpoint.getConnection().setCopy(CONNECTION_COPY);

        this.serverEndpoint = new DirectServer();

        ConsoleListener consoleListener = new ConsoleListener();
        PrintStream writer = new PrintStream(new ConsoleOutputStream(consoleListener.getIn()));

        this.client = new OpenverseClient(clientEndpoint, writer);
        this.server = new OpenverseServer(serverEndpoint, writer);

        consoleListener.setLocalServer(this.server);
        this.consoleThread = new Thread(null, consoleListener::run, "Console thread");
    }

    @Override
    public void onEnable(Scene previous) {
        super.onEnable(previous);

        this.server.join();
        this.client.join(this);

        System.out.println("[Launcher] Setting up connection");
        this.serverEndpoint.newConnection(this.clientEndpoint.getConnection(), CONNECTION_COPY);
        //consoleThread.start();
    }

    @Override
    public void onDisable(Scene next) {
        super.onDisable(next);

        this.server.stop();
        //client.stop()
    }
}
