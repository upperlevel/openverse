package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.common.network.SingleplayerClient;
import xyz.upperlevel.opencraft.common.network.SingleplayerServer;
import xyz.upperlevel.opencraft.common.network.packet.ChunkAreaPacket;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;

public final class ClientListenerSubscriber {

    private ClientListenerSubscriber() {
    }

    public static void subscribe() {
        SingleplayerClient.connection().getListenerManager().ifPresent(listener -> {
            listener.register(ChunkAreaPacket.class, new ChunkAreaPacketListener());
            listener.register(PlayerTeleportPacket.class, new PlayerTeleportPacketListener());
        });
        System.out.println("Client> Listeners registered");
    }
}
