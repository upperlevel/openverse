package xyz.upperlevel.openverse.client.render;

import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.client.resource.model.ClientModel;
import xyz.upperlevel.openverse.client.resource.model.ClientShape;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.resource.model.Shape;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ShapeCompilerManager {

    private final Map<ClientShape, ShapeCompiler> compilersByModel = new HashMap<>();

    public <S extends ClientShape> void register(@NonNull S shape, @NonNull ShapeCompiler<S> compiler) {
        compilersByModel.put(shape, compiler);
    }

    public void unregister(ClientShape shape) {
        compilersByModel.remove(shape);
    }

    @SuppressWarnings("unchecked")
    public <S extends ClientShape> ShapeCompiler<S> getCompiler(S shape) {
        return compilersByModel.get(shape);
    }

    // --- shape

    public int getVerticesCount(ClientShape shape) {
        ShapeCompiler compiler = getCompiler(shape);
        if (compiler != null)
            return compiler.getVerticesCount();
        return 0;
    }

    public int getDataCount(ClientShape shape) {
        ShapeCompiler compiler = getCompiler(shape);
        if (compiler != null)
            return compiler.getDataCount();
        return 0;
    }

    public <S extends ClientShape> int compile(S shape, Matrix4f in, ByteBuffer out) {
        ShapeCompiler<S> compiler = getCompiler(shape);
        if (compiler != null)
            return compiler.compile(shape, in, out);
        return 0;
    }

    // --- model

    public int getVerticesCount(ClientModel model) {
        int count = 0;
        for (ClientShape shape : model.getShapes())
            count += getVerticesCount(shape);
        return count;
    }

    public int getDataCount(ClientModel model) {
        int count = 0;
        for (ClientShape shape : model.getShapes())
            count += getDataCount(shape);
        return count;
    }

    public int compile(ClientModel model, Matrix4f in, ByteBuffer out) {
        int vertices = 0;
        for (ClientShape shape : model.getShapes())
            vertices += compile(shape, in, out);
        return vertices;
    }
}
