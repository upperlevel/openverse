package xyz.upperlevel.opencraft.client.render.model;

import lombok.NonNull;
import xyz.upperlevel.opencraft.resource.model.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ModelCompilers {

    private Map<Class<? extends Model>, ModelCompiler> compilers = new HashMap<>();

    public ModelCompilers() {
    }

    public ModelCompilers register(ModelCompiler compiler) {
        compilers.put(compiler.getModel(), compiler);
        return this;
    }

    public <M extends Model> ModelCompiler<M> get(Class<M> model) {
        return compilers.get(model);
    }

    public Collection<ModelCompiler> get() {
        return compilers.values();
    }

    public void unregister(Class<? extends Model> model) {
        compilers.remove(model);
    }

    public void unregister(@NonNull ModelCompiler compiler) {
        unregister(compiler.getModel());
    }
}