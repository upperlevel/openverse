package xyz.upperlevel.opencraft.client.view;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.common.block.BlockType;
import xyz.upperlevel.opencraft.common.world.Block;

import static xyz.upperlevel.opencraft.common.world.util.CoordConverter.chunkPosY;
import static xyz.upperlevel.opencraft.common.world.util.CoordConverter.chunkPosZ;

public class BlockView implements Block {

    @Getter
    private WorldView world;
    
    @Getter
    private ChunkView chunk;
    
    @Getter
    private int x, y, z;

    private int cpx, cpy, cpz;

    public BlockView(@NonNull WorldView world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        // chunk coords
        int cx = (int) chunkX(x);
        int cy = (int) chunkY(y);
        int cz = (int) chunkZ(z);

        cpx = (int) chunkPosX(x);
        cpy = (int) chunkPosY(y);
        cpz = (int) chunkPosZ(z);

        chunk = world.getChunk(cx, cy, cz);
    }

    public BlockView(@NonNull ChunkView chunk, int x, int y, int z) {
        this.chunk = chunk;
        cpx = x;
        cpy = y;
        cpz = z;

        // world coords
        this.world = chunk.getWorld();
        this.x = (int) worldX(chunk, x);
        this.y = (int) worldY(chunk, y);
        this.z = (int) worldZ(chunk, z);
    }

    @Override
    public BlockType getType() {
        return null;
    }

    @Override
    public void setType(BlockType type) {
        // todo
    }
}