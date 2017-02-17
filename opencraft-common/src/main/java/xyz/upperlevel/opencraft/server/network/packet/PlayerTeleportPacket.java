package xyz.upperlevel.opencraft.server.network.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.utils.packet.packet.Packet;

@AllArgsConstructor
public class PlayerTeleportPacket implements Packet {

    @Getter
    @Setter
    private double x, y, z;

    @Getter
    @Setter
    private float yaw, pitch;
}
