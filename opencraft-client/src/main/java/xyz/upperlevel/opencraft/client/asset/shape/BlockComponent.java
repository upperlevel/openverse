package xyz.upperlevel.opencraft.client.asset.shape;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.render.ViewRenderer;

import java.nio.ByteBuffer;

public interface BlockComponent {

    boolean isInside(float x, float y, float z);

    boolean isInside(Zone3f zone);

    int compile(Matrix4f transformation, ByteBuffer buffer);

    int cleanCompile(int x, int y, int z, ViewRenderer area, Matrix4f transformation, ByteBuffer buffer);
}
