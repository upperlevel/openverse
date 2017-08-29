package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.PacketSide;
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
    private final Logger logger = Logger.getLogger("OpenverseServer");
    private final EventManager eventManager = new EventManager();
    private final Resources resources = new Resources(Logger.getLogger("CIAO"));
    private final PlayerManager playerManager;

    public OpenverseServer(@NonNull Server server) {
        Openverse.setProxy(this);
        this.endpoint = server;
        this.channel = new Channel("main").setProtocol(Openverse.PROTOCOL.compile(PacketSide.SERVER));
        server.setDefaultChannel(channel);
        this.universe = new Universe();
        this.endpoint.setDefaultChannel(channel);
        this.playerManager = new PlayerManager();
    }

    /**
     * This scene is called from launchers to init {@link OpenverseServer}.
     * It will just load resources and other saves (including world).
     */
    public void join() {
        Openverse.resources().setup();
        Openverse.resources().load();
        resources.load();
    }

    public void stop() {
    }

    public static OpenverseServer get() {
        return (OpenverseServer) Openverse.getProxy();
    }
}
