package xyz.upperlevel.openverse.client.resource.model.shape;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.model.shape.ShapeTypeRegistry;

/**
 * This class registries only {@link ClientShapeType} that every one generate {@link ClientShape} objects.
 */
public class ClientShapeTypeRegistry extends ShapeTypeRegistry<ClientShapeType<?>> {
    public ClientShapeTypeRegistry() {
        // do not call super!
        register(new Identifier<>("cube", ClientCube::new));
        // todo sphere
    }
}
