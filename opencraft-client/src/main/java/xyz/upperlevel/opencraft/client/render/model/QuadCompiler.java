package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.resource.model.Quad;

import java.nio.ByteBuffer;

public class QuadCompiler implements ModelCompiler<Quad> {

    @Override
    public Class<Quad> getModel() {
        return Quad.class;
    }

    @Override
    public int compile(Quad model, Matrix4f in, ByteBuffer out) {

        return 0;
    }
}
