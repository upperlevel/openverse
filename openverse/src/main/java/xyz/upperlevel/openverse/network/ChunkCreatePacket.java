package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.world.block.BlockSystem;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

public class ChunkCreatePacket implements Packet {
    @Getter
    private final ChunkLocation location;

    // a 3d-array of block types ids for this chunk
    private final String[][][] blockTypes;

    public ChunkCreatePacket(@NonNull Chunk chunk) {
        this(
                chunk.getWorld().getName(),
                chunk.getLocation(),
                chunk.getBlockSystem()
        );
    }

    public ChunkCreatePacket(@NonNull String world, ChunkLocation location, @NonNull BlockSystem blocks) {
        this(location, new String[16][16][16]);

        for (int ix = 0; ix < 16; ix++) {
            for (int iy = 0; iy < 16; iy++) {
                for (int iz = 0; iz < 16; iz++) {
                    BlockType type = blocks.getBlock(ix, iy, iz).getType();
                    if (type != null)
                        blockTypes[ix][iy][iz] = type.getId();
                }
            }
        }
    }

    public ChunkCreatePacket(ChunkLocation location, @NonNull String[][][] blockTypes) {
        this.location = location;
        this.blockTypes = blockTypes;
    }

    public String getBlockType(int x, int y, int z) {
        return blockTypes[x][y][z];
    }
}
