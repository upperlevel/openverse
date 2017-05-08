package xyz.upperlevel.openverse.resource.model;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelManager<M extends Model> {

    private Map<String, M> models = new HashMap<>();

    public ModelManager() {
    }

    public ModelManager register(@NonNull M model) {
        models.put(model.getId(), model);
        return this;
    }

    public M get(String id) {
        return models.get(id);
    }

    public Collection<M> get() {
        return models.values();
    }

    public void unregister(String id) {
        models.remove(id);
    }

    public void unregister(@NonNull M model) {
        models.remove(model.getId());
    }

    public void clear() {
        models.clear();
    }
}
