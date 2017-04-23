package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.World;

@RequiredArgsConstructor
public  abstract class ChunkSystem {

    //TODO: put the BlockTypes in the parameters
    public static ChunkGenerator defaultGenerator = new FlatWorldGenerator(Chunk.HEIGHT, null, null);


    @Getter
    private final World world;

    public abstract ChunkGenerator getGenerator();

    public abstract void setGenerator(ChunkGenerator generator);

    public abstract Chunk get(int x, int y, int z);
}
