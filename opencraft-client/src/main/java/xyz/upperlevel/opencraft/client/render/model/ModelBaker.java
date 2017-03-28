package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.resource.model.ModelPart;

import java.nio.ByteBuffer;

public interface ModelCompiler<M extends ModelPart> {

    Class<M> getModel();

    int compile(M model, Matrix4f in, ByteBuffer out);
}
