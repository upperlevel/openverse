package xyz.upperlevel.opencraft.common.entity;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityTypeManager {

    private Map<String, EntityType> entities = new HashMap<>();

    public EntityTypeManager() {
    }

    public void add(@NonNull EntityType type) {
        entities.put(type.getId(), type);
    }

    public EntityType get(EntityType type) {
        return type;
    }

    public Collection<EntityType> getAll() {
        return entities.values();
    }
}
