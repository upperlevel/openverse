package xyz.upperlevel.opencraft.client.render.model;

import lombok.NonNull;
import xyz.upperlevel.opencraft.common.shape.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelCompilerManager {

    private Map<Class<?>, ModelCompiler> compilers  =new HashMap<>();

    public ModelCompilerManager() {
    }

    public void register(ModelCompiler comp) {
        compilers.put(comp.getModel(), comp);
    }

    public ModelCompiler get(Model m) {
        return compilers.get(m.getClass());
    }

    public void unregister(@NonNull ModelCompiler comp) {
        compilers.remove(comp.getModel());
    }

    public Collection<ModelCompiler> getAll() {
        return compilers.values();
    }
}
