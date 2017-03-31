package xyz.upperlevel.opencraft.resource.entity;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityTypeManager {

    private Map<String, EntityType> entities = new HashMap<>();

    public EntityTypeManager() {
    }

    public EntityTypeManager register(@NonNull EntityType type) {
        entities.put(type.getId(), type);
        return this;
    }

    public EntityType get(EntityType type) {
        return entities.get(type.getId());
    }

    public EntityTypeManager unregister(String id) {
        entities.remove(id);
        return this;
    }

    public EntityTypeManager unregister(@NonNull EntityType type) {
        entities.remove(type.getId());
        return this;
    }

    public Collection<EntityType> getAll() {
        return entities.values();
    }
}
