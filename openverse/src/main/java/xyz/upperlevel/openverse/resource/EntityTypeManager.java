package xyz.upperlevel.openverse.resource;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityTypeManager {

    private final Map<String, EntityType> types = new HashMap<>();

    public EntityTypeManager register(@NonNull EntityType type) {
        types.put(type.getId(), type);
        return this;
    }

    public EntityType get(String id) {
        return types.get(id);
    }

    public Collection<EntityType> get() {
        return types.values();
    }

    public void unregister(String id) {
        types.remove(id);
    }

    public void unregister(@NonNull BlockType type) {
        types.remove(type.getId());
    }

    public void clear() {
        types.clear();
    }
}
