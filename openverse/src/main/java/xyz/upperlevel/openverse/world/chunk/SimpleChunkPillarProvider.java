package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class SimpleChunkPillarProvider implements ChunkPillarProvider {
    private final World world;
    private final Map<Long, ChunkPillar> chunkPillarsMap = new HashMap<>();

    private static long provideIndex(int x, int z) {
        return ((long) x) << 32 | z;
    }

    @Override
    public ChunkPillar getChunkPillar(int x, int z) {
        long i = provideIndex(x, z);
        if (chunkPillarsMap.containsKey(i)) {
            return chunkPillarsMap.get(i);
        } else {
            ChunkPillar res = new ChunkPillar(world, x, z);
            chunkPillarsMap.put(i, res);
            return res;
        }
    }

    @Override
    public void setChunkPillar(ChunkPillar chunkPillar) {
        chunkPillarsMap.put(provideIndex(chunkPillar.getX(), chunkPillar.getZ()), chunkPillar);
    }

    @Override
    public boolean unloadChunkPillar(int x, int z) {
        return chunkPillarsMap.remove(provideIndex(x, z)) != null;
    }
}