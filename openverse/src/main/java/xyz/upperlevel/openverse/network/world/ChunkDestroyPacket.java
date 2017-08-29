package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

/**
 * This packet is sent from the server to the client when a chunk must be destroyed (when it's out from player view distance).
 */
@Getter
@RequiredArgsConstructor
public class ChunkDestroyPacket implements Packet {
    private final ChunkLocation location;

    @Override
    public void toData(ByteBuf byteBuf) {
        // todo
    }

    @Override
    public void fromData(ByteBuf byteBuf) {
        // todo
    }
}
