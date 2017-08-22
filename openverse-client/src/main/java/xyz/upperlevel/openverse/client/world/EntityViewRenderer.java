package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.EntityRemovePacket;
import xyz.upperlevel.openverse.network.EntitySpawnPacket;
import xyz.upperlevel.openverse.world.entity.Entity;

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
        OpenverseClient.get().getChannel().register(this);
    }

    public void spawn(EntityRenderer entity) {
        entities.put(entity.getId(), entity);
    }

    public void spawn(Entity entity) {
        spawn(new EntityRenderer(entity.getId(), entity.getType()));
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
        entities.values().forEach(EntityRenderer::render);
    }

    // todo remove entities too far from player

    @EventHandler
    public void onEntitySpawn(EntitySpawnPacket packet) {
        spawn(new EntityRenderer(packet.getEntityId(), packet.getEntityType()));
    }

    @EventHandler
    public void onEntityRemove(EntityRemovePacket packet) {
        remove(packet.getEntityId());
    }
}
