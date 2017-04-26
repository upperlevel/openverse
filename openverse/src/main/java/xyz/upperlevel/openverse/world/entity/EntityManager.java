package xyz.upperlevel.openverse.world.entity;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityManager {

    private Map<Integer, BaseEntity> entities = new HashMap<>();

    public EntityManager() {
    }

    public void add(@NonNull BaseEntity entity) {
        int s = entities.size();
        entities.put(s, entity);
        entity.setId(s);
    }

    public BaseEntity get(int id) {
        return entities.get(id);
    }

    public void remove(int id) {
        entities.remove(id);
    }

    public void remove(@NonNull BaseEntity ent) {
        entities.remove(ent.getId());
    }

    public Collection<BaseEntity> getAll() {
        return entities.values();
    }
}
