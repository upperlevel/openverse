package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.resource.model.Model;

import java.nio.ByteBuffer;

public interface ModelCompiler<M extends Model> {

    Class<M> getModel();

    int compile(M model, Matrix4f in, ByteBuffer out);
}
