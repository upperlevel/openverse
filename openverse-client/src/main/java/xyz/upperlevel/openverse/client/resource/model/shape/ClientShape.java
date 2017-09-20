package xyz.upperlevel.openverse.client.resource.model.shape;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.resource.model.shape.Shape;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

/**
 * This interface has been made to separate server shapes from client shapes.
 */
public interface ClientShape extends Shape {
    /**
     * Gets number of vertices taken by this shape.
     */
    int getVerticesCount();

    /**
     * Gets number of data (floats) taken by this shape.
     */
    default int getDataCount() {
        return Vertex.DATA_COUNT * getVerticesCount();
    }

    void setColor(Color color);

    void setTexture(Texture texture);

    /**
     * Stores graphical vertices for this shape in a buffer based
     * on an input matrix.
     */
    int store(Matrix4f in, ByteBuffer buffer);
}