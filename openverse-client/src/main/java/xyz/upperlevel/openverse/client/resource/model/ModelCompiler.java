package xyz.upperlevel.openverse.client.resource.model;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.client.resource.TextureBakery;

import java.nio.ByteBuffer;

public interface ModelCompiler {

    int getVerticesCount();

    int getDataCount();

    int compile(TextureBakery bakery, Matrix4f in, ByteBuffer out);
}
