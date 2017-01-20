package xyz.upperlevel.opencraft.world.block;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.block.shape.BlockShape;

public class BlockId {

    @Getter
    public final String id;

    @Getter
    public final BlockShape shape;

    public BlockId(String name, BlockShape shape) {
        id = name;
        this.shape = shape;
    }

    public BlockState createState() {
        return new BlockState(this);
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