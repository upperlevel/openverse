package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.network.EntityTeleportPacket;
import xyz.upperlevel.openverse.world.Location;

import java.util.HashMap;
import java.util.Map;

public class EntityManager implements Listener {

    @Getter
    private final OpenverseProxy handle;

    private long nextId = 0;
    private final Map<Long, Entity> entitiesById = new HashMap<>();

    public EntityManager(@NonNull OpenverseProxy handle) {
        this.handle = handle;
        handle.getChannel().getEventManager().register(this);
    }

    public void register(Entity entity) {
        entitiesById.put(entity.getId(), entity);
        entity.setId(nextId++);
    }

    public void unregister(long id) {
        entitiesById.remove(id);
    }

    public void unregister(@NonNull Entity entity) {
        entitiesById.remove(entity.getId());
    }

    public void clear() {
        entitiesById.clear();
        nextId = 0;
    }

    @EventHandler
    public void onTeleport(EntityTeleportPacket event) {
        Entity entity = entitiesById.get(event.getId());

        Location location = entity.getLocation();
        location.set(
                event.getX(),
                event.getY(),
                event.getZ(),
                event.getYaw(),
                event.getPitch()
        );
        entity.setLocation(location, false);
    }
}
