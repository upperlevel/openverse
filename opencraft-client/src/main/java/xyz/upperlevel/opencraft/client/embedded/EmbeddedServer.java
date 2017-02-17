package xyz.upperlevel.opencraft.client.embedded;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.utils.packet.Connection;
import xyz.upperlevel.utils.packet.listener.PacketReceiveListenerManager;
import xyz.upperlevel.utils.packet.packet.Packet;

public class EmbeddedServer implements Connection {

    @Getter
    @Setter
    @Accessors(chain = true)
    private PacketReceiveListenerManager listenerManager;

    public EmbeddedServer() {
    }

    public EmbeddedServer(PacketReceiveListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    @Override
    public void sendPacket(Packet packet) {
        listenerManager.onReceive(packet);
    }
}
