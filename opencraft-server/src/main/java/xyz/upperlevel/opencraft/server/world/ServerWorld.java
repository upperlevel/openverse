package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.common.entity.EntityManager;
import xyz.upperlevel.opencraft.common.world.World;

import java.util.HashSet;
import java.util.Set;

public class ServerWorld implements World {

    @Getter
    @Setter
    private ChunkGenerator generator;

    @Getter
    private Set<ServerChunk> loadedChunks = new HashSet<>();

    @Getter
    private EntityManager entityManager = new EntityManager();

    public ServerWorld(@NonNull ChunkGenerator generator) {
        this.generator = generator;
    }

    public void load(@NonNull ServerChunk chunk) {
        loadedChunks.add(chunk);
    }

    public boolean isLoaded(ServerChunk chunk) {
        return loadedChunks.contains(chunk);
    }

    public void unload(int x, int y, int z) {
        loadedChunks.removeIf(c -> c.getX() == x && c.getY() == y && c.getZ() == z);
    }

    public void unload(ServerChunk chunk) {
        loadedChunks.remove(chunk);
    }

    @Override
    public ServerBlock getBlock(int x, int y, int z) {
        int cx = x / 16;
        int cy = y / 16;
        int cz = z / 16;

        int bx = x % 16;
        int by = y % 16;
        int bz = z % 16;

        return getChunk(cx, cy, cz).getBlock(bx, by, bz);
    }

    @Override
    public ServerChunk getChunk(int x, int y, int z) {
        for (ServerChunk c : loadedChunks)
            if (c.getX() == x && c.getY() == y && c.getZ() == z)
                return c;
        ServerChunk c = new ServerChunk(this, x, y, z);
        c.generate();
        return c;
    }
}