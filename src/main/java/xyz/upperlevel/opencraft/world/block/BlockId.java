package xyz.upperlevel.opencraft.world.block;

public class BlockId<State extends BlockState> {

    private final String id;

    public BlockId(String name) {
        id = name;
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