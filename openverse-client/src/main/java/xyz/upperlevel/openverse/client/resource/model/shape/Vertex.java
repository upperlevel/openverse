package xyz.upperlevel.openverse.client.resource.model.shape;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

@Getter
@Setter
public class Vertex {
    public static final int VERTICES_COUNT = 1;
    public static final int DATA_COUNT = 9;

    private float x, y, z;
    private Color color;
    private float u, v;

    public Vertex() {
    }

    public Vertex(Config config) {
        deserialize(config);
    }

    protected void deserialize(Config config) {
        this.x = config.getInt("x", 0);
        this.y = config.getInt("y", 0);
        this.z = config.getInt("z", 0);
        // todo color = ...
        this.u = config.getInt("u", 0);
        this.v = config.getInt("v", 0);
    }

    public int store(Matrix4f in, ByteBuffer buffer, float layer) {
        Vector3f p = in.transformPosition(x, y, z, new Vector3f());
        buffer.putFloat(p.x)
                .putFloat(p.y)
                .putFloat(p.z);

        // color
        buffer.putFloat(color.r)
                .putFloat(color.g)
                .putFloat(color.b)
                .putFloat(color.a);

        // textures coordinates
        buffer.putFloat(u)
                .putFloat(v)
                .putFloat(layer); // texture id

        return VERTICES_COUNT;
    }
}
