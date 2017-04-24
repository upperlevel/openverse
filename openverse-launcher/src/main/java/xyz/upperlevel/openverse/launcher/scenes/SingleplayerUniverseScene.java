package xyz.upperlevel.openverse.launcher.scenes;

import lombok.Getter;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.impl.direct.DirectClient;
import xyz.upperlevel.hermes.server.impl.direct.DirectServer;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.server.OpenverseServer;

public class SingleplayerUniverse {

    @Getter
    private final OpenverseClient client;

    @Getter
    private final OpenverseServer server;

    public SingleplayerUniverse() {
        client = new OpenverseClient(new DirectClient(null));
        server = new OpenverseServer(new DirectServer((Channel) null));
    }
}
