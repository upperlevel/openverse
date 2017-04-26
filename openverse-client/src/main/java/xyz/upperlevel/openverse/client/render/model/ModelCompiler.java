package xyz.upperlevel.openverse.client.render.model;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.resource.model.impl.NodeModel;

import java.nio.ByteBuffer;

public interface ModelCompiler<M extends NodeModel> {

    Class<M> getModel();

    int getVerticesCount();

    int getDataCount();

    default int compile(M model, ByteBuffer out) {
        return compile(model, new Matrix4f(), out);
    }

    int compile(M model, Matrix4f in, ByteBuffer out);
}