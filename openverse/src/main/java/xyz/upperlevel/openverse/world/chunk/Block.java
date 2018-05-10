package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;

@Getter
public class Block {
    private final World world;
    private final int chunkRelativeX, chunkRelativeY, chunkRelativeZ, x, y, z;
    private final Chunk chunk;
    private final BlockStorage storage;

    public Block(Chunk chunk, int chunkRelativeX, int chunkRelativeY, int chunkRelativeZ, BlockStorage storage) {
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.chunkRelativeX = chunkRelativeX;
        this.chunkRelativeY = chunkRelativeY;
        this.chunkRelativeZ = chunkRelativeZ;
        this.x = chunk.getX() * 16 + chunkRelativeX;
        this.y = chunk.getY() * 16 + chunkRelativeY;
        this.z = chunk.getZ() * 16 + chunkRelativeZ;
        this.storage = storage;
    }

    public Block getRelative(int x, int y, int z) {
        return world.getBlock(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    public Block getRelative(@NonNull BlockFace face) {
        switch (face) {
            case UP: return getRelative(0, 1, 0);
            case DOWN: return getRelative(0, -1, 0);
            case LEFT: return getRelative(1, 0, 0);
            case RIGHT: return getRelative(-1, 0, 0);
            case BACK: return getRelative(0, 0, 1);
            case FRONT: return getRelative(0, 0, -1);
            default: throw new IllegalStateException(face.name() + " not expected");
        }
    }


    public BlockType getType() {
        return storage.getBlockState(chunkRelativeX, chunkRelativeY, chunkRelativeZ).getBlockType();
    }

    public void setType(BlockType type) {
        storage.setBlockState(chunkRelativeX, chunkRelativeY, chunkRelativeZ, type.getDefaultBlockState());
    }


    public BlockState getState() {
        return chunk.getBlockState(chunkRelativeX, chunkRelativeY, chunkRelativeZ);
    }

    public void setState(BlockState state) {
        chunk.setBlockState(chunkRelativeX, chunkRelativeY, chunkRelativeZ, state);
    }

    public int getSkyLight() {
        return storage.getBlockSkylight(chunkRelativeX, chunkRelativeY, chunkRelativeZ);
    }

    public int getBlockLight() {
        return storage.getBlockLight(chunkRelativeX, chunkRelativeY, chunkRelativeZ);
    }

    @Override
    public String toString() {
        return "{loc:{" + x + "," + y + "," + z + "},state:" + getState() + ",skyLight:" + getSkyLight() + ",blockLight;" + getBlockLight() + "}";
    }
}