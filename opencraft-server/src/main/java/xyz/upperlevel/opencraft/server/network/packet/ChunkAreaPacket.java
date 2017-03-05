package xyz.upperlevel.opencraft.common.network.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.common.network.ChunkArea;
import xyz.upperlevel.utils.packet.packet.Packet;

@AllArgsConstructor
public class ChunkAreaPacket implements Packet {

    @Getter
    @Setter
    private int x, y, z;

    @Getter
    @Setter
    @NonNull
    private ChunkArea area;
}