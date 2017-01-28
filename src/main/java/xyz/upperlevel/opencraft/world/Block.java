package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.world.block.BlockFacePosition;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.block.BlockIds;
import xyz.upperlevel.opencraft.world.block.BlockState;

import java.util.Objects;

public class Block {

    @Getter
    public final World world;

    @Getter
    public final Chunk chunk;

    @Getter
<<<<<<< HEAD
    public final int chunkX, chunkY, chunkZ;

    @Getter
=======
>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026
    public final int x, y, z;

    @Getter
    @Setter
    @NonNull
    private BlockState state = BlockIds.NULL_BLOCK.generateState();

<<<<<<< HEAD
    Block(Chunk chunk, int x, int y, int z) {
        world = chunk.world;
        this.chunk = chunk;

        // chunk coordinates
        chunkX = x;
        chunkY = y;
        chunkZ = z;

        // world coordinates
        this.x = chunk.toWorldX(x);
        this.y = chunk.toWorldY(y);
        this.z = chunk.toWorldZ(z);
=======
    Block(Chunk chunk, int chunkBlockX, int chunkBlockY, int chunkBlockZ) {
        Objects.requireNonNull(chunk, "Chunk cannot be null.");

        world = chunk.getWorld();
        this.chunk = chunk;

        x = chunk.toWorldX(chunkBlockX);
        y = chunk.toWorldY(chunkBlockY);
        z = chunk.toWorldZ(chunkBlockZ);
>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026
    }

    public BlockId getId() {
        return state.id;
    }

    public void setId(BlockId id) {
        setState(id.generateState());
    }

<<<<<<< HEAD
    public Block getRelative(BlockFacePosition position) {
        return world.getBlock(
                x + position.directionX,
                y + position.directionY,
                z + position.directionZ
=======
    public Block getRelative(int offsetX, int offsetY, int offsetZ) {
        return chunk.getBlock(
                offsetX,
                offsetY,
                offsetZ
        );
    }

    public Block getRelative(Relative relative) {
        return getRelative(
                relative.offsetX,
                relative.offsetY,
                relative.offsetZ
>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026
        );
    }
}