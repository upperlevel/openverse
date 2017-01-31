package xyz.upperlevel.opencraft.render;

import java.nio.ByteBuffer;

public class RenderBus {

    private final ByteBuffer bus;

    public RenderBus position(float x, float y, float z) {
        bus.putFloat(x);
        bus.putFloat(y);
        bus.putFloat(z);
        return this;
    }

    public RenderBus color(float r, float g, float b, float a) {
        bus.putFloat(r);
        bus.putFloat(b);
        bus.putFloat(g);

        bus.putFloat(a);
        return this;
    }

    public RenderBus texture(float u, float v) {
        bus.putFloat(u);
        bus.putFloat(v);
        return this;
    }
}
