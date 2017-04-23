package xyz.upperlevel.openverse.world;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;

public class Block {

    @Getter
    private final World world;

    @Getter
    private final int x, y, z;

    @Getter
    private final Chunk chunk;

    private final int chx, chy, chz;

    public Block(Chunk chunk, int x, int y, int z) {
        checkInside(chunk, x, y, z);
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.x = x;
        this.y = y;
        this.z = z;

        this.chx = x & 0xF;
        this.chy = y % 0xF;
        this.chz = z & 0xF;
    }

    public Block getRelative(int x, int y, int z) {
        return getWorld().getBlock(
                getX() + x,
                getY() + y,
                getZ() + z
        );
    }

    public BlockType getType() {
        return chunk.getData().getType(chx, chy, chz);
    }

    public void setType(BlockType type) {
        chunk.getData().setType(chx, chy, chz, type);
    }


    //The JVM will easily optimize this method away if it's not needed (assert are disabled by default)
    private static void checkInside(Chunk chunk, int x, int y, int z) {
        assert chunk.getX() == (x >> 4);
        assert chunk.getY() == (y >> 4);
        assert chunk.getZ() == (z >> 4);
    }
}