package xyz.upperlevel.opencraft.client.render.entity;

import lombok.NonNull;
import xyz.upperlevel.opencraft.client.world.entity.EntityType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityRendererProvider {

    private Map<EntityType, EntityRenderer> renderers = new HashMap<>();

    public EntityRendererProvider() {
    }

    public void register(@NonNull EntityRenderer renderer) {
        renderers.put(renderer.getType(), renderer);
    }

    public void unregister(EntityType type) {
        renderers.remove(type);
    }

    public void unregister(@NonNull EntityRenderer renderer) {
        renderers.remove(renderer.getType());
    }

    public Collection<EntityRenderer> getRenderers() {
        return renderers.values();
    }
}