package xyz.upperlevel.opencraft.world.block;


import java.util.Objects;

public abstract class BlockId {

    public final String id;
    private BlockShape shape = BlockShape.empty();

    public BlockId(String id) {
        this.id = id;
    }

    public BlockId(String id, BlockShape shape) {
        this(id);
        this.shape = shape;
    }

    public final String getId() {
        return id;
    }

    public BlockShape getShape() {
        return shape;
    }

    public void setShape(BlockShape shape) {
        Objects.requireNonNull(shape, "Block id shape cannot be null");
        this.shape = shape;
    }

    public abstract BlockState generateState();

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BlockId && id.equals(((BlockId) object).id);
    }

    @Override
    public String toString() {
        return id;
    }
}