package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

public class SendChunkPacket implements Packet {

    @Getter
    private final String world;

    @Getter
    private final int x, y, z;

    // a 3d-array of block types ids for this chunk
    private final String[][][] blockTypes;

    public SendChunkPacket(@NonNull Chunk chunk) {
        this(chunk.getWorld().getName(),
                chunk.getLocation(),
                chunk.getData());
    }

    public SendChunkPacket(@NonNull String world, int x, int y, int z, @NonNull ChunkData chunkData) {
        this(world, x, y, z, new String[16][16][16]);

        for (int ix = 0; ix < 16; ix++) {
            for (int iy = 0; iy < 16; iy++) {
                for (int iz = 0; iz < 16; iz++) {
                    BlockType type = chunkData.getType(ix, iy, iz);
                    if (type != null)
                        blockTypes[ix][iy][iz] = type.getId();
                }
            }
        }
    }

    public SendChunkPacket(@NonNull String world, int x, int y, int z, @NonNull String[][][] blockTypes) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockTypes = blockTypes;
    }

    public ChunkLocation getLocation() {
        return new ChunkLocation(x, y, z);
    }

    public String getBlockType(int x, int y, int z) {
        return blockTypes[x][y][z];
    }
}
