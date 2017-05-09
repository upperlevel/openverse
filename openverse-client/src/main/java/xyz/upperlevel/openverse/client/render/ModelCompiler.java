package xyz.upperlevel.openverse.client.render;

import xyz.upperlevel.openverse.resource.model.Model;

import java.nio.ByteBuffer;

public interface ModelCompiler<M extends Model> {

    void compile(M model, ByteBuffer buffer);
}
