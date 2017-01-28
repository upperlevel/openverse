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
    public final int x, y, z;

    @Getter
    @Setter
    @NonNull
    private BlockState state = BlockIds.NULL_BLOCK.generateState();

    Block(Chunk chunk, int chunkBlockX, int chunkBlockY, int chunkBlockZ) {
        Objects.requireNonNull(chunk, "Chunk cannot be null.");

        world = chunk.getWorld();
        this.chunk = chunk;

        x = chunk.toWorldX(chunkBlockX);
        y = chunk.toWorldY(chunkBlockY);
        z = chunk.toWorldZ(chunkBlockZ);
    }

    public BlockId getId() {
        return state.id;
    }

    public void setId(BlockId id) {
        setState(id.generateState());
    }

    public Block getRelative(int offsetX, int offsetY, int offsetZ) {
        return chunk.getBlock(
                x + offsetX,
                y + offsetY,
                z + offsetZ
        );
    }

    public Block getRelative(Relative relative) {
        return getRelative(
                relative.offsetX,
                relative.offsetY,
                relative.offsetZ
        );
    }
}