package xyz.upperlevel.opencraft.client.render.shape;

import lombok.NonNull;
import xyz.upperlevel.opencraft.client.asset.shape.ShapeComponent;

import java.util.HashMap;
import java.util.Map;

public class ComponentRendererProvider {

    private Map<Class<? extends ShapeComponent>, ComponentRenderer> renderers = new HashMap<>();

    public ComponentRendererProvider() {
    }

    public void register(@NonNull ComponentRenderer renderer) {
        renderers.put(renderer.getComponentType(), renderer);
    }

    public  void unregister(Class<? extends ShapeComponent> componentClass) {
        renderers.remove(componentClass);
    }

    public void unregister(@NonNull ShapeComponent component) {
        renderers.remove(component.getClass());
    }

    public void unregister(@NonNull ComponentRenderer renderer) {
        renderers.remove(renderer.getComponentType());
    }

    public ComponentRenderer getRenderer(ShapeComponent component) {
        return renderers.get(component);
    }
}
