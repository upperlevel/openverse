package xyz.upperlevel.opencraft.client.world;

import lombok.NonNull;
import xyz.upperlevel.opencraft.client.world.entity.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LocalWorld {

    private LocalChunk[][][] chunks = new LocalChunk[][][];

    private Map<Long, Entity> entities = new HashMap<>();

    public LocalWorld() {
    }

    public LocalChunk getChunk(int x, int y, int z) {
        
    }

    public void spawnEntity(float x, float y, float z, @NonNull Entity entity) {
        entities.put(entity.getId(), entity);
        entity.setPosition(x, y, z);
    }

    public void spawnEntity(float x, float y, float z, float yaw, float pitch, @NonNull Entity entity) {
        entities.put(entity.getId(), entity);
        entity.setPosition(x, y, z, yaw, pitch);
    }

    public void removeEntity(long id) {
        entities.remove(id);
    }

    public void removeEntity(@NonNull Entity entity) {
        entities.remove(entity.getId());
    }

    public Entity getEntity(long id) {
        return entities.get(id);
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }
}