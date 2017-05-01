package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.event.impl.ConnectionCloseEvent;
import xyz.upperlevel.hermes.event.impl.ConnectionOpenEvent;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.server.resource.ServerResourceManager;
import xyz.upperlevel.openverse.server.world.entity.ServerPlayer;
import xyz.upperlevel.openverse.world.Universe;

import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.Player;

public class OpenverseServer implements OpenverseProxy {

    private final Server server;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private final EventManager eventManager = new EventManager();

    @Getter
    private final ServerResourceManager resourceManager = new ServerResourceManager();

    @Getter
    private final EntityManager entityManager;

    @Getter
    private final ServerPlayerManager playerManager;

    // we don't know if the server is locally connected or use the network
    public OpenverseServer(@NonNull Server server) {
        this.server = server;
        server.setDefaultChannel(channel);
        
        entityManager = new EntityManager();
        playerManager = new ServerPlayerManager();
    }

    public void start() {
        // REGISTERS LISTENER

        server.getEventManager().register(ConnectionOpenEvent.class, event -> {
            // when connect add the player to the players list
            ServerPlayer player = new ServerPlayer("hello_world", event.getConnection());
            players.put(player.getName(), player);
        });

        server.getEventManager().register(ConnectionCloseEvent.class, event -> {
            // when disconnect removes the player from the players list
            Player player = getPlayer(event.getConnection());
            players.remove(player.getName());
        });

        resourceManager.load();
    }

    @Override
    public Universe getUniverse() {
        return null;
    }

    @Override
    public Server getEndpoint() {
        return server;
    }
}
