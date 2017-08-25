package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import static xyz.upperlevel.openverse.network.SerialUtil.readChunkLocation;
import static xyz.upperlevel.openverse.network.SerialUtil.writeChunkLocation;

@AllArgsConstructor
@NoArgsConstructor
public class ChunkUnloadPacket implements Packet {
    @Getter
    private ChunkLocation location;

    @Override
    public void toData(ByteBuf out) {
        writeChunkLocation(location, out);
    }

    @Override
    public void fromData(ByteBuf in) {
        location = readChunkLocation(in);
    }
}
