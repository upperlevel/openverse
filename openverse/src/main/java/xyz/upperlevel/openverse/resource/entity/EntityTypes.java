package xyz.upperlevel.openverse.resource.entity;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityTypes {

    private Map<String, EntityType> entities = new HashMap<>();

    public EntityTypes() {
    }

    public EntityTypes register(@NonNull EntityType type) {
        entities.put(type.getId(), type);
        return this;
    }

    public EntityType get(EntityType type) {
        return entities.get(type.getId());
    }

    public Collection<EntityType> get() {
        return entities.values();
    }

    public void unregister(String id) {
        entities.remove(id);
    }

    public void unregister(@NonNull EntityType entity) {
        unregister(entity.getId());
    }
}
