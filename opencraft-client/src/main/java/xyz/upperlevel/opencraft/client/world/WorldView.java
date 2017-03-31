package xyz.upperlevel.opencraft.client.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.opencraft.resource.block.BlockType;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.ChunkData;
import xyz.upperlevel.opencraft.world.World;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

import static java.lang.Math.floor;
import static java.lang.Math.floorMod;
import static xyz.upperlevel.opencraft.world.Chunk.*;

public class WorldView implements World {

    // chunk

    public class ChunkView implements Chunk {

        @Getter
        private int x, y, z;

        @Getter
        private ChunkData data = new ChunkData();

        public ChunkView(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public World getWorld() {
            return WorldView.this;
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            return new BlockView(x, y, z);
        }

        public void render(Uniformer uniformer) {

        }

        // block

        public class BlockView implements Block {

            @Getter
            private int x, y, z;

            public BlockView(int x, int y, int z) {
                this.x = x;
                this.y = y;
                this.z = z;
            }

            @Override
            public World getWorld() {
                return WorldView.this;
            }

            @Override
            public Chunk getChunk() {
                return ChunkView.this;
            }

            @Override
            public BlockType getType() {
                int bx = floorMod(x, WIDTH);
                int by = floorMod(y, HEIGHT);
                int bz = floorMod(z, LENGTH);

                return data.getType(bx, by, bz);
            }

            @Override
            public void setType(BlockType type) {
                int bx = floorMod(x, WIDTH);
                int by = floorMod(y, HEIGHT);
                int bz = floorMod(z, LENGTH);

                data.setType(bx, by, bz, type);
            }
        }
    }

    // world

    @Getter
    private int radius, side;

    @Getter
    @Setter
    private int x, y, z;

    @Getter
    private Chunk[][][] chunks;

    public WorldView(int radius) {
        this.radius = radius;
        side = radius * 2 + 1;
        chunks = new ChunkView[side][side][side];
    }

    public void setCenter(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        // todo load new chunks
    }

    private int getIndexX(int x) {
        return x - this.x + radius;
    }

    private int getIndexY(int y) {
        return y - this.y + radius;
    }

    private int getIndexZ(int z) {
        return z - this.z + radius;
    }

    @Override
    public Chunk getChunk(int x, int y, int z) {
        int lx = getIndexX(x);
        int ly = getIndexY(y);
        int lz = getIndexZ(z);

        if (lx < 0 || lx >= side || ly < 0 || ly >= side || lz < 0 || lz >= side)
            return null;
        return chunks[lx][ly][lz];
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        int lx = (int) floor(x / (float) WIDTH);
        int ly = (int) floor(y / (float) HEIGHT);
        int lz = (int) floor(z / (float) LENGTH);

        int bx = floorMod(x, WIDTH);
        int by = floorMod(y, HEIGHT);
        int bz = floorMod(z, LENGTH);

        Chunk c = getChunk(lx, ly, lz);
        return c != null ? c.getBlock(bx, by, bz) : null;
    }

    public void render(Uniformer uniformer) {
        for (int x = 0; x < side; x++) {
            for (int y = 0; y < side; y++) {
                for (int z = 0; z < side; z++) {
                    ChunkView chunk = (ChunkView) chunks[x][y][z];
                    if (chunk != null)
                        chunk.render(uniformer);
                }
            }
        }
    }
}
