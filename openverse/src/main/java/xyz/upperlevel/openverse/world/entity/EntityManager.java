package xyz.upperlevel.openverse.world.entity;

import lombok.NonNull;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.entity.EntityChangeVelocityPacket;
import xyz.upperlevel.openverse.network.world.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.world.Location;

import java.util.HashMap;
import java.util.Map;

public class EntityManager implements PacketListener {
    private int nextId = 0;
    //TODO: use a more specific class
    private final Map<Integer, Entity> entitiesById = new HashMap<>();

    public EntityManager() {
        Openverse.getProxy().getChannel().register(this);
    }

    public void register(Entity entity) {
        entitiesById.put(entity.getId(), entity);
        entity.setId(nextId++);
    }

    public void unregister(int id) {
        entitiesById.remove(id);
    }

    public void unregister(@NonNull Entity entity) {
        entitiesById.remove(entity.getId());
    }

    public Entity get(int id) {
        return entitiesById.get(id);
    }

    public void clear() {
        entitiesById.clear();
        nextId = 0;
    }

    public void onTick() {
        entitiesById.values().forEach(Entity::onTick);
    }

    @PacketHandler
    public void onTeleport(Connection sender, EntityTeleportPacket event) {
        Entity entity = entitiesById.get(event.getEntityId());

        Location location = new Location(entity.getWorld(), event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch());
        entity.setLocation(location);
    }

    @PacketHandler
    public void onVelocityChange(Connection sender, EntityChangeVelocityPacket event) {
        Entity entity = entitiesById.get(event.getEntityId());

        entity.setVelocity(entity.getVelocity());
    }
}
