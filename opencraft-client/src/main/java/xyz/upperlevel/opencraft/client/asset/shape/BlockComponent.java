package xyz.upperlevel.opencraft.client.asset.shape;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.render.RenderArea;

import java.nio.ByteBuffer;

public interface BlockShapeComponent extends Zone {

    String getId();

    void compile(Matrix4f transformation, ByteBuffer buffer);

    void cleanCompile(int x, int y, int z, RenderArea area, Matrix4f transformation, ByteBuffer buffer);
}
