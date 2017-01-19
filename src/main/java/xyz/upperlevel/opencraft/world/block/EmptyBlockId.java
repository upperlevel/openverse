package xyz.upperlevel.opencraft.world.block;

public class EmptyBlockId extends BlockId {

    public static final EmptyBlockId $ = new EmptyBlockId();

    public final BlockState emptyState = new BlockState(this);

    public EmptyBlockId() {
        super("empty_block");
    }

    @Override
    public BlockState createState() {
        return emptyState;
    }

    public BlockState getEmptyState() {
        return emptyState;
    }

    public static EmptyBlockId $() {
        return $;
    }
}