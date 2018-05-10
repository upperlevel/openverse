package xyz.upperlevel.openverse.item;

import xyz.upperlevel.openverse.Openverse;

public class ItemTypes {
    public static final ItemType AIR = ItemType.AIR;
    public static final BlockItemType DIRT;
    public static final BlockItemType GRASS;

    static {
        ItemTypeRegistry reg = Openverse.resources().itemTypes();
        Openverse.getLogger().info("Init ItemTypes");
        DIRT = (BlockItemType) reg.entry("dirt");
        GRASS = (BlockItemType) reg.entry("grass");
    }
}
