package xyz.upperlevel.openverse.world.chunk;

import lombok.NonNull;
import xyz.upperlevel.openverse.resource.BlockType;

public class ChunkData {

    protected BlockType[][][] types = new BlockType[Chunk.WIDTH][Chunk.HEIGHT][Chunk.LENGTH];

    public BlockType getType(int x, int y, int z) {
        return types[x][y][z];
    }

    public void setType(int x, int y, int z, @NonNull BlockType type) {
        types[x][y][z] = type;
    }
}