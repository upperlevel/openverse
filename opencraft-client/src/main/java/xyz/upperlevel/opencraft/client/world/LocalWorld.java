package xyz.upperlevel.opencraft.client.world;

import lombok.NonNull;
import xyz.upperlevel.opencraft.client.world.entity.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LocalWorld {

    private Map<Long, Entity> entities = new HashMap<>();

    public LocalWorld() {
    }

    public void spawn(float x, float y, float z, @NonNull Entity entity) {
        entities.put(entity.getId(), entity);
        entity.setPosition(x, y, z);
    }

    public void spawn(float x, float y, float z, float yaw, float pitch, @NonNull Entity entity) {
        entities.put(entity.getId(), entity);
        entity.setPosition(x, y, z, yaw, pitch);
    }

    public void remove(long id) {
        entities.remove(id);
    }

    public void remove(@NonNull Entity entity) {
        entities.remove(entity.getId());
    }

    public Entity getEntity(long id) {
        return entities.get(id);
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }
}