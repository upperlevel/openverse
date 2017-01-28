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
    public final int chunkX, chunkY, chunkZ;

    @Getter
    public final int x, y, z;

    @Getter
    @Setter
    @NonNull
    private BlockState state = BlockIds.NULL_BLOCK.generateState();

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
    }

    public BlockId getId() {
        return state.id;
    }

    public void setId(BlockId id) {
        setState(id.generateState());
    }

    public Block getRelative(BlockFacePosition position) {
        return world.getBlock(
                x + position.directionX,
                y + position.directionY,
                z + position.directionZ
        );
    }
}