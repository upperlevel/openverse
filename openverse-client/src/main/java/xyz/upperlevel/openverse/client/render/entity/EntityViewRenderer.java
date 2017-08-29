package xyz.upperlevel.openverse.client.render.entity;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.entity.EntityRemovePacket;
import xyz.upperlevel.openverse.network.entity.EntitySpawnPacket;
import xyz.upperlevel.openverse.network.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.event.EntityMoveEvent;
import xyz.upperlevel.openverse.world.entity.event.EntitySpawnEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class render all entities in a view with a specified distance.
 * <b>This class does not render the player.</b>
 * It might be synchronized with player location and used to render world entities.
 */
public class EntityViewRenderer implements Listener {
    // todo add render distance
    private final Map<Integer, EntityRenderer> entities = new HashMap<>();

    public EntityViewRenderer() {
       // OpenverseClient.get().getChannel().register(this);
    }

    public void spawn(EntityRenderer entity) {
        entities.put(entity.getId(), entity);
    }

    public void spawn(Entity entity) {
        spawn(new EntityRenderer(entity));
    }

    public void remove(int id) {
        entities.remove(id);
    }

    public void remove(EntityRenderer entity) {
        entities.remove(entity.getId());
    }

    public EntityRenderer getEntity(int id) {
        return entities.get(id);
    }

    public Collection<EntityRenderer> getEntities() {
        return entities.values();
    }

    public void render() {
        getEntities().forEach(EntityRenderer::render);
    }

    public void destroy() {
        getEntities().forEach(EntityRenderer::destroy);
    }

    // todo remove entities too far from player

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        spawn(new EntityRenderer(e.getEntity()));
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent e) {
        EntityRenderer ent = getEntity(e.getEntity().getId());
        if (ent != null)
            ent.setLocation(e.getLocation());
    }

    @EventHandler
    public void onEntityRemove(EntityRemovePacket packet) {
        remove(packet.getEntityId());
    }
}
