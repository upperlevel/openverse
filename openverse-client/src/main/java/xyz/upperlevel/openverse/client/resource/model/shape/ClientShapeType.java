package xyz.upperlevel.openverse.client.resource.model.shape;

import xyz.upperlevel.openverse.resource.model.shape.ShapeType;
import xyz.upperlevel.openverse.util.config.Config;

/**
 * This class generates only {@link ClientShape} objects.
 */
public interface ClientShapeType<S extends ClientShape> extends ShapeType<S> {
    S create(Config config);
}
