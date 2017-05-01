package xyz.upperlevel.openverse.resource.model;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelManager {

    private Map<String, Model> models = new HashMap<>();

    public ModelManager() {
    }

    public ModelManager register(@NonNull Model model) {
        models.put(model.getId(), model);
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

    public void unregister(@NonNull Model model) {
        models.remove(model.getId());
    }

    public void clear() {
        models.clear();
    }
}
