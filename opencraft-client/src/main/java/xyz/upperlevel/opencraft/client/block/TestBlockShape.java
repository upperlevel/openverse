package xyz.upperlevel.opencraft.client.block;

public class TestBlockShape {

    public static final BlockShape inst = new BlockShape();

    static {
        inst.addComponent(new BlockShapeComponent());
    }
}