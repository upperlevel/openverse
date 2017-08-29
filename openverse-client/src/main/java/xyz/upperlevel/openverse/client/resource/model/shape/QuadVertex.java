package xyz.upperlevel.openverse.client.resource.model.shape;

import lombok.Getter;
import xyz.upperlevel.openverse.util.config.Config;

/**
 * A vertex specified for a quad.
 */
@Getter
public class QuadVertex extends Vertex {
    private final QuadVertexPosition position;

    public QuadVertex(QuadVertexPosition position) {
        this.position = position;
        setupDefaults();
    }

    public QuadVertex(QuadVertexPosition position, Config config) {
        this(position);
        super.deserialize(config);
    }

    private void setupDefaults() {
        setX(position.getX());
        setY(position.getY());
        setZ(position.getZ());
        setU(position.getU());
        setV(position.getV());
    }
}
