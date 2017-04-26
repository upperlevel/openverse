package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.hermes.event.ConnectionEventManager;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.network.EntityTeleportPacketReceiveListener;
import xyz.upperlevel.openverse.client.network.UniverseInfoPacketReceiveListener;
import xyz.upperlevel.openverse.client.resource.ClientResourceManager;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.network.EntityTeleportPacket;
import xyz.upperlevel.openverse.client.world.entity.ClientPlayer;
import xyz.upperlevel.openverse.network.GetUniversePacket;
import xyz.upperlevel.openverse.network.UniverseInfoPacket;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.List;

import static java.util.Collections.singletonList;

public class OpenverseClient implements OpenverseProxy, Scene {//TODO Implement

    @Getter
    private final Client client;

    @Getter
    private final Universe<ClientWorld> universe;

    @Getter
    private final WorldViewer viewer;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private ClientResourceManager resourceManager = new ClientResourceManager();

    @Getter
    private final ClientPlayer player;//TODO: initialize player

    public OpenverseClient(@NonNull Client client) {
        this.client = client;

        // if connection is closed refuse
        Connection conn = client.getConnection();
        conn.setDefaultChannel(channel);
        if (!conn.isOpen())
            throw new IllegalStateException("Client connection is closed");

        universe = new Universe<>();
        viewer = new WorldViewer();
    }

    @Override
    public void onEnable(Scene prev) {
        resourceManager.load(); // loads resources first

        // here we send all initialize packets
        Connection conn = client.getConnection();

        conn.send(channel, new GetUniversePacket());

        // here we register all listener to packets received
        ConnectionEventManager events = channel.getEventManager();

        events.register(UniverseInfoPacket.class,   new UniverseInfoPacketReceiveListener(this));
        events.register(EntityTeleportPacket.class, new EntityTeleportPacketReceiveListener(this));
    }

    @Override
    public void onDisable(Scene prev) {
        try {
            client.stop();
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void onRender() {

    }

    /**
     * Gets main player.
     */
    public WorldViewer getPlayer() {
        return viewer;
    }

    @Override
    public List<Player> getPlayers() {
        return singletonList(getPlayer());
    }

    @Override
    public Client getEndpoint() {
        return client;
    }
}
