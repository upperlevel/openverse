package xyz.upperlevel.openverse.resource.model.shape;

import xyz.upperlevel.openverse.resource.Registry;

/**
 * The {@link ShapeTypeRegistry} deserves a list of all {@link ShapeType} registered.
 */
public class ShapeTypeRegistry<T extends ShapeType> extends Registry<T> {
    public ShapeTypeRegistry() {
        // todo add server-side cube to this registry!
    }
}