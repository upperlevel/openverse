package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.util.math.Aabb3d;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.List;

@Getter
public class BlockType {
    public static final BlockType AIR = new BlockType("air", false) {
        @Override
        public Aabb3d getCollisionBox(World world, BlockState state, int x, int y, int z) {
            return Aabb3d.ZERO;
        }
    };
    public static final Aabb3d FULL_BLOCK_AABB = new Aabb3d(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    private final String id;
    @Setter
    private int rawId = -1;

    protected boolean opaque;

    protected final BlockStateRegistry stateRegistry;
    private BlockState defaultBlockState;

    public BlockType(String id) {
        this.id = id;
        this.stateRegistry = createBlockState();
        setDefaultState(stateRegistry.getDefaultState());
    }

    public BlockType(String id, boolean opaque) {
        this(id);
        this.opaque = opaque;
    }


    public BlockStateRegistry createBlockState() {
        return BlockStateRegistry.of(this); //Creates a BlockStateRegistry with no propriety
    }

    public void setDefaultState(BlockState state) {
        this.defaultBlockState = state;
    }

    public BlockState getBlockState(int meta) {
        return stateRegistry.getState(meta);
    }

    public int getStateMeta(BlockState state) {
        return state.getId();
    }

    public int getFullId(BlockState state) {
        return rawId | (state.getId() & 0xF);
    }

    public void addCollisionBoxes(BlockState state, Entity entity, int x, int y, int z, Aabb3d entityBox, List<Aabb3d> res) {
        Aabb3d blockBox = getCollisionBox(entity.getWorld(), state, x, y, z);
        if (blockBox != Aabb3d.ZERO) {
            blockBox = blockBox.translate(x, y, z);
            if (blockBox.intersect(entityBox)) {
                res.add(blockBox);
            }
        }
    }

    public Aabb3d getCollisionBox(World world, BlockState state, int x, int y, int z) {
        return FULL_BLOCK_AABB;
    }

    /**
     * Creates {@link BlockEntity} for this block. If none, returns {@code null}.
     */
    public BlockEntity createBlockEntity(BlockState state) {
        return null;
    }

    public BlockState getStateWhenPlaced(Player placer, int x, int y, int z) {
        return getDefaultBlockState();
    }

    @Override
    public int hashCode() {
        //Cannot use rawId because it could (and does) change overtime
        return id.hashCode();
    }

    public boolean collisionRaytrace(BlockState state, World world, int posX, int posY, int posZ, double startX, double startY, double startZ, double endX, double endY, double endZ) {
        Aabb3d box = getCollisionBox(world, state, posX, posY, posZ).translate(posX, posY, posZ);
        return box.rayTest(startX, startY, startZ, endX, endY, endZ);
    }
}