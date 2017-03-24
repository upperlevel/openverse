package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class World {

    @Getter
    @Setter
    private ChunkGenerator gen;

    @Getter
    private Set<Chunk> loaded = new HashSet<>();

    public World() {
    }

    public World(ChunkGenerator gen) {
        this.gen = gen;
    }

    public void load(@NonNull Chunk chunk) {
        loaded.add(chunk);
    }

    public boolean isLoaded(Chunk chunk) {
        return loaded.contains(chunk);
    }

    public Chunk get(int x, int y, int z) {
        for (Chunk c : loaded)
            if (c.getX() == x && c.getY() == y && c.getZ() == z)
                return c;
        return new Chunk(this, x, y, z);
    }

    public void unload(int x, int y, int z) {
        loaded.removeIf(c -> c.getX() == x && c.getY() == y && c.getZ() == z);
    }

    public void unload(Chunk chunk) {
        loaded.remove(chunk);
    }
}