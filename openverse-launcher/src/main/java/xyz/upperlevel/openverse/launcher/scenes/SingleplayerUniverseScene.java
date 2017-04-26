package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.client.impl.direct.DirectClientConnection;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.hermes.server.impl.direct.DirectServerConnection;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

/**
 * This stage that handles client connection to a singleplayer universe.
 * It has a client and a server that are connected each other.
 */
public class SingleplayerUniverseScene extends Stage {

    @Getter
    private final OpenverseClient client;

    @Getter
    private final OpenverseServer server;

    public SingleplayerUniverseScene() {
        // creates client and server and connect them together
        DirectClient drCl = new DirectClient();
        DirectClientConnection clCon = drCl.getConnection();
        client = new OpenverseClient(new DirectClient());

        DirectServer drSe = new DirectServer();
        DirectServerConnection seCon = drSe.newConnection(drCl.getConnection());
        server = new OpenverseServer(new DirectServer());

        seCon.setOther(clCon);

        // todo put copy to false if all works
        clCon.setCopy(true);
        seCon.setCopy(true);
    }

    @Override
    public void onEnable(Scene prev) {
        stage(new SingleplayerResourceLoadingScene(this));
        super.onEnable(prev);
    }
}
