package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.world.Chunk;

public class ServerChunk implements Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    @Getter
    private ServerWorld world;

    @Getter
    private int x, y, z;

    @Getter
    private ChunkStorer container = new ChunkStorer();

    public ServerChunk(@NonNull ServerWorld world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void load() {
        world.load(this);
    }

    public boolean isLoaded() {
        return world.isLoaded(this);
    }

    public void unload() {
        world.unload(this);
    }

    public void generate() {
        world.getGenerator().generate(this);
    }

    @Override
    public ServerBlock getBlock(int x, int y, int z) {
        return new ServerBlock(this, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ServerChunk) {
            ServerChunk c = (ServerChunk) o;
            return world.equals(c.getWorld()) && x == c.getX() && y == c.getY() && z == c.getZ();
        }
        return false;
    }
}
