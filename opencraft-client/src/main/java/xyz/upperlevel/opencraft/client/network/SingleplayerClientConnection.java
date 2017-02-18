package xyz.upperlevel.opencraft.client.network;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.opencraft.server.network.SingleplayerServerConnection;
import xyz.upperlevel.utils.packet.Connection;
import xyz.upperlevel.utils.packet.listener.PacketListenerManager;
import xyz.upperlevel.utils.packet.packet.Packet;

import java.util.Optional;

public class SingleplayerClientConnection implements Connection {

    public static final SingleplayerClientConnection $ = new SingleplayerClientConnection();

    @Setter
    private PacketListenerManager listenerManager = new PacketListenerManager();

    public SingleplayerClientConnection() {
    }

    public Optional<PacketListenerManager> getListenerManager() {
        return Optional.ofNullable(listenerManager);
    }

    @Override
    public void sendPacket(Packet packet) {
        SingleplayerServerConnection.$()
                .getListenerManager()
                .ifPresent(listenerManager -> listenerManager.onReceive(packet));
    }

    public static SingleplayerClientConnection $() {
        return $;
    }
}
