package xyz.upperlevel.openverse.client.render.compiler;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.client.render.ShapeCompiler;
import xyz.upperlevel.openverse.client.resource.model.ClientSphere;

import java.nio.ByteBuffer;

// todo
public class SphereCompiler implements ShapeCompiler<ClientSphere> {

    @Override
    public int getVerticesCount() {
        return 0;
    }

    @Override
    public int getDataCount() {
        return 0;
    }

    @Override
    public int compile(ClientSphere shape, Matrix4f in, ByteBuffer out) {
        return 0;
    }
}
