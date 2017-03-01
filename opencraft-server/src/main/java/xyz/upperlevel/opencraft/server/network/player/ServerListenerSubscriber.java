package xyz.upperlevel.opencraft.server.network.player;

import xyz.upperlevel.opencraft.common.network.SingleplayerServer;
import xyz.upperlevel.opencraft.common.network.packet.AskChunkAreaPacket;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;

public final class ServerListenerSubscriber {

    private ServerListenerSubscriber() {
    }

    public static void subscribe() {
        System.out.println("Server listener subscribed");
        SingleplayerServer.connection().getListenerManager().ifPresent(listener -> {
            listener.register(AskChunkAreaPacket.class, new AskChunkAreaPacketListener());
            listener.register(PlayerTeleportPacket.class, new PlayerTeleportPacketListener());
        });
    }
}
