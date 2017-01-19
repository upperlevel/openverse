package xyz.upperlevel.opencraft.world.block;

import lombok.NonNull;

public class BlockId<State extends BlockState> {

    @NonNull
    public final String str;

    public BlockId(String name) {
        str = name;
    }

    public BlockState createState() {
        return new BlockState(this);
    }

    @Override
    public int hashCode() {
        return str.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlockId && str.equals(((BlockId) obj).str);
    }

    @Override
    public String toString() {
        return str;
    }
}