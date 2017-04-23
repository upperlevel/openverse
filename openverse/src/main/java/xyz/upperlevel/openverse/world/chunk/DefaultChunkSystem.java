package xyz.upperlevel.openverse.world.chunk;

import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

public class DefaultChunkSystem extends BaseChunkSystem {

    private Map<ChunkLoc, Chunk> chunks = new HashMap<>();

    public DefaultChunkSystem(World world) {
        super(world);
    }


    public void set0(Chunk chunk) {
        chunks.put(ChunkLoc.loc(chunk), chunk);
    }

    public void remove(Chunk chunk) {
        chunks.remove(ChunkLoc.loc(chunk));
    }

    @Override
    public Chunk get0(int x, int y, int z) {
        return chunks.get(new ChunkLoc(x, y, z));
    }

    /*
     * This is a location 3d location class optimized for SinglePlayer storage:
     * The hashCode is different for chunks that are near each other
     * the first 11 bits are taken by the x parameter
     * the next 11 bits are taken by the z parameter
     * and tha last 10 bits are taken by the y
     * 11 + 11 + 10 = 32
     */
    public static class ChunkLoc {
        public final int x, y, z;
        private final int hashCode;

        public ChunkLoc(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;

            hashCode =      (x & 0x07FF) << 21 |
                            (z & 0x07FF) << 10 |
                            (y & 0x03FF);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object other) {
            if(other instanceof ChunkLoc) {
                ChunkLoc c = (ChunkLoc) other;
                return  c.x == x &&
                        c.y == y &&
                        c.z == z;
            } else return false;
        }

        public static ChunkLoc loc(Chunk chunk) {
            return new ChunkLoc(chunk.getX(), chunk.getY(), chunk.getZ());
        }
    }
}
