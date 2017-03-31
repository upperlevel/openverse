package xyz.upperlevel.opencraft.client.render.model;

import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.render.RenderContext;
import xyz.upperlevel.opencraft.resource.model.Shape;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ModelBakery {

    private Map<Class<? extends Shape>, Compiler> compilers = new HashMap<>();

    public ModelBakery() {
    }

    public void register(Compiler baker) {
        compilers.put(baker.getModelClass(), baker);
    }

    public <M extends Shape> Compiler<M> get(M shape) {
        return compilers.get(shape.getClass());
    }

    public void unregister(@NonNull Compiler baker) {
        compilers.remove(baker.getModelClass());
    }

    public Collection<Compiler> getAll() {
        return compilers.values();
    }

    public int bake(Shape shape, RenderContext bakery, ByteBuffer out) {
        return get(shape).bake(shape, bakery, out);
    }

    public int bake(Shape shape, RenderContext bakery, Matrix4f in, ByteBuffer out) {
        return get(shape).bake(shape, bakery, in, out);
    }
}
