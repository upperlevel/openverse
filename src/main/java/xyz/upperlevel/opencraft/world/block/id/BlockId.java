package xyz.upperlevel.opencraft.world.block.id;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.world.block.state.BlockState;
import xyz.upperlevel.opencraft.world.block.shape.BlockShape;

public class BlockId {

    @Getter
    public final String id;

    @Getter
    @Setter
    @NonNull
    private BlockShape shape = BlockShape.empty();

    public BlockId(String name) {
        id = name;
    }

    public BlockId(String name, BlockShape shape) {
        this(name);
        this.shape = shape;
    }

    public BlockState generateState() {
        return new BlockState(this, shape.copy());
    }

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