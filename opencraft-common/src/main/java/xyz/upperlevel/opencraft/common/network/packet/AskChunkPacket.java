package xyz.upperlevel.opencraft.common.network.packet;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.utils.packet.packet.Packet;

public class AskChunkPacket implements Packet {

    @Getter
    @Setter
    private int x, y, z;

    public AskChunkPacket(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
