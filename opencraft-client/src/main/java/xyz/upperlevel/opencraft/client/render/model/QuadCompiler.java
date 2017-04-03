package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.render.Rendering;
import xyz.upperlevel.opencraft.resource.model.impl.Quad;
import xyz.upperlevel.opencraft.resource.model.impl.Vertex;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

public class QuadCompiler implements ModelCompiler<Quad> {

    public QuadCompiler() {
    }

    @Override
    public Class<Quad> getModel() {
        return Quad.class;
    }

    @Override
    public int getVerticesCount() {
        return 4;
    }

    @Override
    public int getDataCount() {
        return getVerticesCount() * 7;
    }

    @Override
    public int compile(Quad model, Matrix4f in, ByteBuffer out) {
        int texture = Rendering.get()
                .textures()
                .getId(model.getTexture());

        for (Vertex v : model.getVertices()) {
            // position
            Vector3f p = in.transformPosition(v.getX(), v.getY(), v.getZ(), new Vector3f());
            out.putFloat(p.x)
                    .putFloat(p.y)
                    .putFloat(p.z);

            // color
            Color c = v.getColor();
            out.putFloat(c.r)
                    .putFloat(c.g)
                    .putFloat(c.b)
                    .putFloat(c.a);

            // tex coords
            out.putFloat(v.getU())
                    .putFloat(v.getV())
                    .putFloat(texture);
        }
        return 4;
    }
}