package xyz.upperlevel.openverse.item;

import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.resource.Registry;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.world.block.BlockTypes;

import java.util.Arrays;
import java.util.stream.Stream;

import static xyz.upperlevel.openverse.item.ItemType.AIR;

public class ItemTypeRegistry extends Registry<ItemType> {
    private int nextId = 0;
    private DynamicArray<ItemType> idRegistry = new DynamicArray<>(256);

    public ItemTypeRegistry(BlockTypeRegistry blockRegistry) {//TODO remove parameter
        register(AIR);
        registerForBlock(BlockTypes.DIRT);
        registerForBlock(BlockTypes.GRASS);
    }

    public void register(ItemType type) {
        register(type.getId(), type);
    }

    public void registerForBlock(BlockType type) {
        register(ItemType.createFromBlock(type));
    }

    public ItemType entry(int id) {
        return idRegistry.get(id);
    }

    public void registerId(ItemType type) {
        idRegistry.set(nextId, type);
        type.setRawId(nextId);
        nextId++;
    }

    @SuppressWarnings("unchecked")
    public Stream<ItemType> getOrderedEntries() {
        return (Stream) Arrays.stream(idRegistry.getArray(), 0, nextId);
    }
}
