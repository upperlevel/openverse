package xyz.upperlevel.openverse.resource.model.shape;

import xyz.upperlevel.openverse.util.config.Config;

/**
 * A {@link ShapeType} instantiate a new {@link Shape} object for a specified type.
 * @param <S> used to use different shapes for client and server side.
 */
public interface ShapeType<S extends Shape> {
    S create(Config config);
}
