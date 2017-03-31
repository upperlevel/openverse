package xyz.upperlevel.opencraft.world.entity;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityManager {

    private Map<Integer, Entity> entities = new HashMap<>();

    public EntityManager() {
    }

    public void add(@NonNull Entity entity) {
        int s = entities.size();
        entities.put(s, entity);
        entity.setId(s);
    }

    public Entity get(int id) {
        return entities.get(id);
    }

    public void remove(int id) {
        entities.remove(id);
    }

    public void remove(@NonNull Entity ent) {
        entities.remove(ent.getId());
    }

    public Collection<Entity> getAll() {
        return entities.values();
    }
}
