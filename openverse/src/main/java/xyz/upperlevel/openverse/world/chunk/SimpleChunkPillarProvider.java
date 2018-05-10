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

    public static long provideIndex(int x, int z) {
        return ((long) x << 32) | ((long) z) & 0xFFFF_FFFFL;
    }

    @Override
    public ChunkPillar getChunkPillar(int x, int z) {
        return chunkPillarsMap.get(provideIndex(x, z));
    }

    @Override
    public void setChunkPillar(ChunkPillar chunkPillar) {
        chunkPillarsMap.put(provideIndex(chunkPillar.getX(), chunkPillar.getZ()), chunkPillar);
    }

    @Override
    public boolean hasPillar(int x, int z) {
        return chunkPillarsMap.containsKey(provideIndex(x, z));
    }

    @Override
    public ChunkPillar unloadChunkPillar(int x, int z) {
        return chunkPillarsMap.remove(provideIndex(x, z));
    }
}
