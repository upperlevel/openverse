package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.utils.packet.Connection;
import xyz.upperlevel.utils.packet.listener.PacketReceiveListenerManager;
import xyz.upperlevel.utils.packet.packet.Packet;

public class SinglePlayerCommunication implements Connection {

    public static final SinglePlayerCommunication $ = new SinglePlayerCommunication();

    @Getter
    @Setter
    @Accessors(chain = true)
    private PacketReceiveListenerManager packetReceiveListenerManager;

    public SinglePlayerCommunication() {
    }

    public SinglePlayerCommunication(PacketReceiveListenerManager packetReceiveListenerManager) {
        this.packetReceiveListenerManager = packetReceiveListenerManager;
    }

    @Override
    public void sendPacket(Packet packet) {
        packetReceiveListenerManager.onReceive(packet);
    }

    public static SinglePlayerCommunication $() {
        return $;
    }
}
