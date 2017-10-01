package xyz.upperlevel.openverse.world.entity;

import xyz.upperlevel.hermes.util.DynamicArray;
import xyz.upperlevel.openverse.resource.Registry;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Arrays;
import java.util.stream.Stream;

public class EntityTypeRegistry extends Registry<EntityType> {
    private int nextId = 0;
    private DynamicArray<EntityType> idRegistry = new DynamicArray<>(256);

    public EntityTypeRegistry() {
        register(Player.TYPE);
    }

    public void register(EntityType type) {
        register(type.getId(), type);
    }

    public EntityType entry(int id) {
        return idRegistry.get(id);
    }

    public void registerId(EntityType type) {
        idRegistry.set(nextId, type);
        type.setRawId(nextId);
        nextId++;
    }

    @SuppressWarnings("unchecked")
    public Stream<EntityType> getOrderedEntries() {
        return (Stream) Arrays.stream(idRegistry.getArray(), 0, nextId);
    }
}
