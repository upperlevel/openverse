package xyz.upperlevel.openverse.resource;

import lombok.NonNull;
import xyz.upperlevel.openverse.resource.model.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelManager {

    private Map<String, Model> models = new HashMap<>();

    public ModelManager() {
    }

    public ModelManager register(@NonNull String id, @NonNull Model model) {
        models.put(id, model);
        return this;
    }

    public Model get(String id) {
        return models.get(id);
    }

    public Collection<Model> get() {
        return models.values();
    }

    public void unregister(String id) {
        models.remove(id);
    }
}
