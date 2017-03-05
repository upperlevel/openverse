package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.server.network.SingleplayerClient;
import xyz.upperlevel.opencraft.server.network.packet.ChunkAreaPacket;
import xyz.upperlevel.opencraft.server.network.packet.PlayerTeleportPacket;

public final class ClientListenerSubscriber {

    private ClientListenerSubscriber() {
    }

    public static void subscribe() {
        SingleplayerClient.connection().getListenerManager().ifPresent(listener -> {
            listener.register(ChunkAreaPacket.class, new ChunkAreaPacketListener());
            listener.register(PlayerTeleportPacket.class, new PlayerTeleportPacketListener());
        });
    }
}
