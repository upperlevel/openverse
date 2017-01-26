package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.World;

@RequiredArgsConstructor
public class WorldViewer {

    // world position

    @Getter
    @NonNull
    public final World world;

    @Getter
    @Setter
    private double x = 0, y = 0, z = 0;

    // rendering info

    @Getter
    @Setter
    private float fov = 50f;

    public WorldViewer(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Chunk getChunk() {
        return world.getChunk(
                getChunkX(),
                getChunkY(),
                getChunkZ()
        );
    }

    public int getChunkX() {
        return world.getChunkX(x);
    }

    public int getChunkY() {
        return world.getChunkY(y);
    }

    public int getChunkZ() {
        return world.getChunkZ(z);
    }
}