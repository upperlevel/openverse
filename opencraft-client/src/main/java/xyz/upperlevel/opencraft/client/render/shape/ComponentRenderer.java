package xyz.upperlevel.opencraft.client.render.shape;

import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.asset.shape.ShapeComponent;

import java.nio.ByteBuffer;

public interface ComponentRenderer<C extends ShapeComponent> {

    Class<C> getComponentType();

    void compile(C component, Matrix4f matrix, ByteBuffer bus);
}
