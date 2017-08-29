package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.network.SerialUtil;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

import static xyz.upperlevel.openverse.network.SerialUtil.readChunkLocation;
import static xyz.upperlevel.openverse.network.SerialUtil.writeChunkLocation;

/**
 * This packet is sent from the server to the client when a chunk must be destroyed (when it's out from player view distance).
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChunkDestroyPacket implements Packet {
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
