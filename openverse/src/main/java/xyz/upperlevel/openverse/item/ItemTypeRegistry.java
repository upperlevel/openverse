package xyz.upperlevel.openverse.item;

import lombok.Getter;
import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.resource.Registry;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

import java.util.Arrays;
import java.util.stream.Stream;

public class ItemTypeRegistry extends Registry<ItemType> {
    @Getter
    private final OpenverseProxy module;

    private int nextId = 0;
    private final DynamicArray<ItemType> idRegistry = new DynamicArray<>(256);

    public ItemTypeRegistry(OpenverseProxy module, BlockTypeRegistry blockTypeRegistry) {
        this.module = module;

        register(ItemType.AIR);
        registerForBlock(blockTypeRegistry.getDirt());
        registerForBlock(blockTypeRegistry.getGrass());
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

    public ItemType getAir() {
        return ItemType.AIR;
    }

    public BlockItemType getDirt() {
        return (BlockItemType) entry("dirt");
    }

    public BlockItemType getGrass() {
        return (BlockItemType) entry("grass");
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
