package xyz.upperlevel.opencraft.client.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.resource.block.BlockType;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.ChunkData;
import xyz.upperlevel.opencraft.world.World;

import static xyz.upperlevel.opencraft.world.Chunk.*;

public class RenderChunk implements Chunk {

    public static class Data implements ChunkData {

        @Getter
        private int vertices;

        @Getter
        private BlockType[][][] types = new BlockType[WIDTH][HEIGHT][LENGTH];

        protected Data() {
        }

        @Override
        public BlockType getType(int x, int y, int z) {
            return types[x][y][z];
        }

        @Override
        public void setType(int x, int y, int z, BlockType type) {
            types[x][y][z] = type;
        }
    }

    @Getter
    private World world;

    @Getter
    private int x, y, z;

    @Getter
    private Data data = new Data();

    public RenderChunk(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void render() {

    }
}
