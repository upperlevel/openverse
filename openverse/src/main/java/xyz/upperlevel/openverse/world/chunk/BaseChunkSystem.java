package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.World;

public abstract class BaseChunkSystem extends ChunkSystem {

    @Getter
    @Setter
    private ChunkGenerator generator = ChunkSystem.defaultGenerator;

    public BaseChunkSystem(World world) {
        super(world);
    }

    @Override
    public Chunk get(int x, int y, int z) {
        Chunk chunk = get0(x, y, z);
        if(chunk == null) {
            chunk = new Chunk(newChunkData(), getWorld(), x, y, z);
            generator.generate(chunk);
            set0(chunk);
        }
        return chunk;
    }

    protected ChunkData newChunkData() {
        return new ChunkData();
    }

    protected abstract Chunk get0(int x, int y, int z);

    protected abstract void set0(Chunk chunk);
}
