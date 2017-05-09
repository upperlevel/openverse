package xyz.upperlevel.openverse.client.render;

import lombok.NonNull;
import xyz.upperlevel.openverse.resource.model.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelCompilerManager {

    private final Map<Model, ModelCompiler> compilersByModel = new HashMap<>();

    public <M extends Model> void register(@NonNull M model, @NonNull ModelCompiler<M> compiler) {
        compilersByModel.put(model, compiler);
    }

    public <M extends Model> ModelCompiler<M> getCompiler(M model) {
        return compilersByModel.get(model);
    }

    public void unregister(Model model) {
        compilersByModel.remove(model);
    }
}
