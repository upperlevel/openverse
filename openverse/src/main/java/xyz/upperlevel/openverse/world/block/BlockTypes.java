package xyz.upperlevel.openverse.world.block;

import xyz.upperlevel.openverse.Openverse;

public final class BlockTypes {
    public static final BlockType AIR = BlockType.AIR;
    public static final BlockType DIRT;
    public static final BlockType GRASS;

    static {
        BlockTypeRegistry reg = Openverse.resources().blockTypes();
        Openverse.getLogger().info("Init BlockTypes");
        DIRT = reg.entry("dirt");
        GRASS = reg.entry("grass");
    }

    private BlockTypes(){}
}
