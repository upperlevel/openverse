package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.ulge.util.Color;

@Accessors(chain = true)
public class QuadVertex {

    public static final int VERTICES_COUNT = 1;

    public static final int DATA_COUNT = 3 + 4 + 2;

    @Getter
    @Setter
    private float x, y, z;

    @Getter
    @Setter
    private float
            r = 1f,
            g = 1f,
            b = 1f,
            a = 1f;

    @Getter
    @Setter
    private float u, v;

    public QuadVertex() {
    }

    public Color getColor() {
        return Color.rgba(r, g, b, a);
    }

    public void setColor(Color color) {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }
}