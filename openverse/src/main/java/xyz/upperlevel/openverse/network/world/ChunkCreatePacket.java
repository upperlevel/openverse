package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@NoArgsConstructor
public class ChunkCreatePacket implements Packet {
    private int x, y, z;
    private int blockTypes[];

    public ChunkCreatePacket(Chunk chunk) {
        this.x = chunk.getX();
        this.y = chunk.getY();
        this.z = chunk.getZ();
        this.blockTypes = new int[16 * 16 * 16];
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockType bType = chunk.getBlockType(x, y, z);
                    if (bType != null && bType != BlockType.AIR) {
                        blockTypes[x << 8 | y << 4 | z] = bType.getRawId();
                    }
                }
            }
        }
    }

    public Chunk resolveChunk(World world) {
        Chunk chunk = world.getChunkPillar(x, z).getChunk(y);
        for (int i = 0; i < 16 * 16 * 16; i++) {
            if (blockTypes[i] != 0) {
                BlockType bType = Openverse.resources().blockTypes().entry(blockTypes[i]);
                if (bType == null) {
                    Openverse.logger().warning("Unresolved id in ChunkCreatePacket: " + blockTypes[i]);
                }
                chunk.setBlockType(i >> 8, i >> 4 & 0xF, i & 0xF, bType);
            }
        }
        return chunk;
    }

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        for (int i = 0; i < 16 * 16 * 16; i++)
            out.writeInt(blockTypes[i]);
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        blockTypes = new int[16 * 16 * 16];
        for (int i = 0; i < blockTypes.length; i++)
            blockTypes[i] = in.readInt();
    }
}
