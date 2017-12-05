package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

/**
 * This packet is sent from the server to the client when a chunk must be destroyed.
 * Usually sent when the chunk is out from server's view distance.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChunkDestroyPacket implements Packet {
    private int x, y, z;

    public ChunkDestroyPacket(ChunkLocation location) {
        x = location.x;
        y = location.y;
        z = location.z;
    }

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }
}
