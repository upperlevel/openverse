package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.resource.Resources;
import xyz.upperlevel.openverse.server.world.Universe;

import java.util.logging.Logger;

@Getter
public class OpenverseServer implements OpenverseProxy, Listener {
    private final Server endpoint;
    private Channel channel;
    private Universe universe;
    private final EventManager eventManager = new EventManager();
    private final Resources resources = new Resources(Logger.getLogger("CIAO"));
    private final PlayerManager playerManager;

    // we don't know if the server is locally connected or use the network
    public OpenverseServer(@NonNull Server server) {
        Openverse.setProxy(this);
        this.endpoint = server;
        //this.channel = new Channel("main").setProtocol(OpenverseProtocol.get());
        this.endpoint.setDefaultChannel(channel);
        this.playerManager = new PlayerManager();
    }

    public void start() {
        resources.load();
    }

    public void stop() {
    }

    public static OpenverseServer get() {
        return (OpenverseServer) Openverse.getProxy();
    }

    @Override
    public Resources getResources() {
        return null;
    }
}
