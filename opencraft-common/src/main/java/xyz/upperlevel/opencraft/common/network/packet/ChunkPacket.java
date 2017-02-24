package xyz.upperlevel.opencraft.common.network.packet;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.opencraft.common.world.CommonChunk;
import xyz.upperlevel.utils.packet.packet.Packet;

public class ChunkPacket implements Packet {

    @Getter
    @Setter
    private CommonChunk chunk;
}
