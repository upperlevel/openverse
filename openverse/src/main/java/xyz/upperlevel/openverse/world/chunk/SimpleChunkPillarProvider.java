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
    private final Map<ChunkPillarLocation, ChunkPillar> chunkPillarsMap = new HashMap<>();

    public ChunkPillar getChunkPillar(ChunkPillarLocation location) {
        return chunkPillarsMap.computeIfAbsent(location, (key) -> new ChunkPillar(world, location.x, location.z));
    }

    @Override
    public ChunkPillar getChunkPillar(int x, int z) {
        return getChunkPillar(new ChunkPillarLocation(x, z));
    }

    @Override
    public void setChunkPillar(int x, int z, ChunkPillar chunkPillar) {
        chunkPillarsMap.put(new ChunkPillarLocation(x, z), chunkPillar);
    }

    @Override
    public boolean unloadChunkPillar(int x, int z) {
        return chunkPillarsMap.remove(new ChunkPillarLocation(x, z)) != null;
    }
}
