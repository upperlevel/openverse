package xyz.upperlevel.opencraft.client.world.entity;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityTypeRegistry {

    private Map<String, EntityType> types = new HashMap<>();

    public EntityTypeRegistry() {
    }

    public void register(@NonNull EntityType type) {
        types.put(type.getId(), type);
    }

    public void unregister(String id) {
        types.remove(id);
    }

    public void unregister(@NonNull EntityType type) {
        types.remove(type.getId());
    }

    public EntityType getType(String id) {
        return types.get(id);
    }

    public Collection<EntityType> getTypes() {
        return types.values();
    }
}
