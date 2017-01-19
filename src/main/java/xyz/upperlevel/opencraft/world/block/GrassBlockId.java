package xyz.upperlevel.opencraft.world.block;

public class GrassBlockId extends BlockId {

    public static final GrassBlockId $ = new GrassBlockId();

    protected GrassBlockId() {
        super("grass_test");
    }

    public static GrassBlockId $() {
        return $;
    }
}