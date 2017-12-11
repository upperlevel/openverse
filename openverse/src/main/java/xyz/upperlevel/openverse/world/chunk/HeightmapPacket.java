package xyz.upperlevel.openverse.world.chunk;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import xyz.upperlevel.hermes.Packet;

@Getter
public class HeightmapPacket implements Packet {
    private int x, z;
    private int[] heightmap;

    public HeightmapPacket() {
    }

    public HeightmapPacket(ChunkPillar pillar) {
        this.x = pillar.getX();
        this.z = pillar.getZ();
        this.heightmap = pillar.getHeightmap();
    }

    @Override
    public void toData(ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(z);
        for (int i = 0; i < 256; i++) {
            byteBuf.writeInt(heightmap[i]);
        }
    }

    @Override
    public void fromData(ByteBuf byteBuf) {
        x = byteBuf.readInt();
        z = byteBuf.readInt();
        heightmap = new int[256];
        for (int i = 0; i < 256; i++) {
            heightmap[i] = byteBuf.readInt();
        }
    }
}
