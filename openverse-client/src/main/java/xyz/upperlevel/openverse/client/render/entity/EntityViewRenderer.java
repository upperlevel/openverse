package xyz.upperlevel.openverse.client.render.entity;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.world.entity.EntityRemovePacket;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.EntityType;
import xyz.upperlevel.openverse.world.entity.event.EntityMoveEvent;
import xyz.upperlevel.openverse.world.entity.event.EntitySpawnEvent;

import java.util.*;

/**
 * This class render all entities in a view with a specified distance.
 * <b>This class does not render the player.</b>
 * It might be synchronized with player location and used to render world entities.
 */
public class EntityViewRenderer implements Listener {
    // todo add render distance

    private final OpenverseClient client;

    private Map<EntityType, EntityRenderer> renderers = new HashMap<>();
    private Set<Entity> entities = new HashSet<>();

    public EntityViewRenderer(OpenverseClient client) {
        this.client = client;

       // OpenverseClient.get().getChannel().register(this);
    }

    public void spawn(Entity entity) {
        entities.add(entity);
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public EntityRenderer getRenderer(Entity entity) {
        return renderers.get(entity.getType());
    }

    public Collection<Entity> getEntities() {
        return entities;
    }

    public void render() {
        /*for (Entity entity : entities) {
            EntityRenderer renderer = getRenderer(entity);
            renderer.render(entity);
        }*/
        //TODO: render
    }

    public void destroy() {
        renderers.values().forEach(EntityRenderer::destroy);
    }

    // todo remove entities too far from player

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        spawn(e.getEntity());
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent e) {
        //TODO
    }

    @EventHandler
    public void onEntityRemove(EntityRemovePacket packet) {
        remove(client.getEntityManager().get(packet.getEntityId()));
    }
}
