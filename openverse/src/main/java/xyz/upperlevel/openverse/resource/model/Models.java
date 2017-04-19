package xyz.upperlevel.openverse.resource.model;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Models {

    private Map<String, Model> models = new HashMap<>();

    public Models() {
    }

    public void load() {
    }

    public void unload() {
        models.clear();
    }

    public Models register(@NonNull String id, @NonNull Model model) {
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
