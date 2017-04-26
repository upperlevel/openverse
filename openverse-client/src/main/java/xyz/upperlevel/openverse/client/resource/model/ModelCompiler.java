package xyz.upperlevel.openverse.client.resource.model;

import org.joml.Matrix4f;

import java.nio.ByteBuffer;

public interface ModelCompiler {

    int getVerticesCount();

    int getDataCount();

    int compile(Matrix4f in, ByteBuffer out);
}
