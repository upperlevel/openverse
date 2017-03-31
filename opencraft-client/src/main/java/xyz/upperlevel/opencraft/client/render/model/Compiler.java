package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.render.RenderContext;
import xyz.upperlevel.opencraft.resource.model.Shape;

import java.nio.ByteBuffer;

public interface Compiler<M extends Shape> {

    /**
     * The class of the model compilable by this compiler.
     */
    Class<M> getModelClass();

    default int bake(M model, RenderContext bakery, ByteBuffer out) {
        return bake(model, bakery, new Matrix4f(), out);
    }

    /**
     * Put model vertices in a byte buffer with input transformations.
     * @param model Model to encode
     * @param bakery Compilation context
     * @param in Input transformation
     * @param out Out buffer
     * @return The number of vertices added
     */
    int bake(M model, RenderContext bakery, Matrix4f in, ByteBuffer out);
}