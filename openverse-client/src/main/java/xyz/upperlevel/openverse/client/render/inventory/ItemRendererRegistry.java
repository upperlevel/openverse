package xyz.upperlevel.openverse.client.render.inventory;

import xyz.upperlevel.openverse.item.BlockItemType;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.item.ItemTypeRegistry;

import java.util.HashMap;
import java.util.Map;

public class ItemRendererRegistry {
    private Map<ItemType, ItemRenderer> renderers = new HashMap<>();

    public void registerDefaults(ItemTypeRegistry registry) {
        registerForBlock(registry.getDirt());
        registerForBlock(registry.getGrass());
    }

    public void register(ItemType type, ItemRenderer renderer) {
        if (renderers.putIfAbsent(type, renderer) != null) {
            throw new IllegalStateException("Renderer already registered for type: " + type.getId());
        }
    }

    public void registerForBlock(BlockItemType type) {
        register(type, new BlockItemRenderer(type.getBlockType()));
    }

    public boolean override(ItemType type, ItemRenderer renderer) {
        return renderers.put(type, renderer) != null;
    }

    public boolean unregister(ItemType type) {
        return renderers.remove(type) != null;
    }

    public ItemRenderer get(ItemType type) {
        return renderers.get(type);
    }
}
