package xyz.upperlevel.opencraft.client.resource.model;

import org.joml.Matrix4f;

import java.nio.ByteBuffer;

public interface ModelCompiler {

    int compile(Matrix4f trans, ByteBuffer buf);
}