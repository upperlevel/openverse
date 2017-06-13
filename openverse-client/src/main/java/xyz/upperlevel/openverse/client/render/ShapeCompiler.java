package xyz.upperlevel.openverse.client.render;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.resource.model.Shape;

import java.nio.ByteBuffer;

public interface ShapeCompiler<S extends Shape> {

    int getVerticesCount();

    int getDataCount();

    int compile(S shape, Matrix4f in, ByteBuffer out);
}
