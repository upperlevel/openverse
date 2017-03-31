package xyz.upperlevel.opencraft.client.render.model.impl;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.render.RenderContext;
import xyz.upperlevel.opencraft.client.render.model.Compiler;
import xyz.upperlevel.opencraft.client.resource.model.impl.Quad;
import xyz.upperlevel.opencraft.client.resource.model.impl.Vertex;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

public class QuadCompiler implements Compiler<Quad> {

    @Override
    public Class<Quad> getModelClass() {
        return Quad.class;
    }

    @Override
    public int bake(Quad model, RenderContext bakery, Matrix4f in, ByteBuffer out) {
        int texture = bakery.getTextureBakery().getId(model.getTexture());

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