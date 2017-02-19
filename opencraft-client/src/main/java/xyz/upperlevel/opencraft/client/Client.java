package xyz.upperlevel.opencraft.client;

import xyz.upperlevel.opencraft.client.network.SingleplayerClient;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.utils.event.EventListener;

public final class Client {

    public static final WorldViewer viewer = new WorldViewer();

    static {
        SingleplayerClient.connection().getListenerManager().ifPresent(manager -> {
            manager.register(PlayerTeleportPacket.class, new EventListener<PlayerTeleportPacket>() {
                @Override
                public byte getPriority() {
                    return 0;
                }

                @Override
                public void call(PlayerTeleportPacket event) {
                    viewer.teleport(
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

    private Client() {
    }

    public static WorldViewer viewer() {
        return viewer;
    }
}