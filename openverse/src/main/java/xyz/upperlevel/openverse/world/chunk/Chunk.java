package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.openverse.world.chunk.storage.SimpleBlockStorage;

@Getter
@Setter
public class Chunk {
    private final World world;
    private final ChunkPillar chunkPillar;
    private final int x, y, z;
    private final ChunkLocation location;

    private BlockStorage blockStorage;

    public Chunk(ChunkPillar chunkPillar, int y) {
        this.world = chunkPillar.getWorld();
        this.chunkPillar = chunkPillar;
        this.x = chunkPillar.getX();
        this.y = y;
        this.z = chunkPillar.getZ();
        this.location = new ChunkLocation(x, y, z);
        this.blockStorage = new SimpleBlockStorage(this);
    }

    public Block getBlock(int x, int y, int z) {
        return blockStorage.getBlock(x, y, z);
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blockStorage.getBlockType(x, y, z);
    }

    public void setBlockType(int x, int y, int z, BlockType type) {
        blockStorage.setBlockType(x, y, z, type);
    }
}