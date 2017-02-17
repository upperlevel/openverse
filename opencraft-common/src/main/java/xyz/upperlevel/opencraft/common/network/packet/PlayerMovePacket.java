package xyz.upperlevel.opencraft.common.network.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.utils.packets.packet.Packet;

@AllArgsConstructor
public class PlayerMovePacket implements Packet {

    @Getter
    @Setter
    private double x, y, z;
}
