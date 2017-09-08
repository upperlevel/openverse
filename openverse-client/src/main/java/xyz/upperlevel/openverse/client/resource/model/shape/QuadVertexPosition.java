package xyz.upperlevel.openverse.client.resource.model.shape;

import lombok.Getter;

@Getter
public enum QuadVertexPosition {
    TOP_LEFT(0, 1, 0, 0 ,0),
    TOP_RIGHT(1, 1, 0, 1, 0),
    BOTTOM_RIGHT(1, 0, 0, 1, 1),
    BOTTOM_LEFT(0, 0, 0, 0, 1);

    private final static QuadVertexPosition[] positions;

    private float x, y, z;
    private float u, v;

    static {
        positions = values();
    }

    QuadVertexPosition(float x, float y, float z, float u, float v) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = u;
        this.v = v;
    }

    public static QuadVertexPosition[] positions() {
        return positions;
    }
}
