package xyz.upperlevel.opencraft.world.block;

public class BlockState {

    public final BlockId id;
    private BlockShape shape = BlockShape.empty();

    public BlockState(BlockId id) {
        this.id = id;
    }

    public BlockState(BlockId id, BlockShape shape) {
        this(id);
        setShape(shape);
    }

    public final BlockId getId() {
        return id;
    }

    public BlockShape getShape() {
        return shape;
    }

    public void setShape(BlockShape shape) {
        this.shape = shape != null ? shape : BlockShape.empty();
    }
}