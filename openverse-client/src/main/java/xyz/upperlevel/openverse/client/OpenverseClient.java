package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.resource.Resources;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.game.Stage;

import java.util.logging.Logger;

@Getter
public class OpenverseClient extends Stage implements OpenverseProxy {
    private static OpenverseClient instance;

    private final Client endpoint;
    private Channel channel;
    private final Player player;
    private final ClientResources resourceManager; // resources are loaded per universe
    private final EventManager eventManager;

    public OpenverseClient(@NonNull Client client) {
        Openverse.setProxy(this);
        instance = this; // for static access

        endpoint = client;
        Connection connection = client.getConnection();
        //channel = new Channel("main").setProtocol(OpenverseProtocol.get());
        connection.setDefaultChannel(channel);

        player = new Player(new Location(null, 0, 0, 0), "my_player", connection);

        eventManager = new EventManager();
        resourceManager = new ClientResources(Logger.getLogger("OpenverseClient"));
    }

    public static OpenverseClient get() {
        return instance;
    }

    @Override
    public ClientResources getResources() {
        return new ClientResources(Logger.getLogger("ClientResources"));
    }
}
