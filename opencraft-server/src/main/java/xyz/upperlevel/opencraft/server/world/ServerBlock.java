package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.resource.block.BlockType;
import xyz.upperlevel.opencraft.world.Block;

import static java.lang.Math.floor;
import static java.lang.Math.floorMod;

public class ServerBlock implements Block {

    @Getter
    private ServerWorld world;

    @Getter
    private ServerChunk chunk;

    @Getter
    private int x, y, z;

    private int cbx, cby, cbz;

    public ServerBlock(@NonNull ServerWorld world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        // gets chunk coords
        int cx = (int) floor(x / 16.);
        int cy = (int) floor(y / 16.);
        int cz = (int) floor(z / 16.);

        cbx = floorMod(x, 16);
        cby = floorMod(y, 16);
        cbz = floorMod(z, 16);

        chunk = world.getChunk(cx, cy, cz);
    }

    public ServerBlock(@NonNull ServerChunk chunk, int x, int y, int z) {
        this.chunk = chunk;
        cbx = x;
        cby = y;
        cbz = z;

        // gets world coords
        world = chunk.getWorld();
        this.x = chunk.getX() * 16 + x;
        this.y = chunk.getY() * 16 + y;
        this.z = chunk.getZ() * 16 + z;
    }

    @Override
    public BlockType getType() {
        return chunk.getData().getVoxel(cbx, cby, cbz);
    }

    @Override
    public void setType(BlockType type) {
        chunk.getData().setVoxel(cbx, cby, cbz, type);
    }
}
