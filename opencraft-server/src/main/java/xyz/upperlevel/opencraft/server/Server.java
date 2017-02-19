package xyz.upperlevel.opencraft.server;

import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.server.network.SingleplayerServer;
import xyz.upperlevel.opencraft.server.world.Player;
import xyz.upperlevel.utils.event.EventListener;

public final class Server {

    private static final Player player = new Player();

    static {
        SingleplayerServer.connection().getListenerManager().ifPresent(manager -> {
            manager.register(PlayerTeleportPacket.class, new EventListener<PlayerTeleportPacket>() {
                @Override
                public byte getPriority() {
                    return 0;
                }

                @Override
                public void call(PlayerTeleportPacket event) {
                    player.getLocation().teleport(
                            event.getX(),
                            event.getY(),
                            event.getZ(),
                            event.getYaw(),
                            event.getPitch(),
                            false
                    );
                }
            });
        });
    }

    private Server() {
    }
}
