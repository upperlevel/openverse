package xyz.upperlevel.opencraft.client.resource.model;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelManager {

    private Map<String, Model> models = new HashMap<>();

    public ModelManager() {
    }

    public void register(@NonNull Model model) {
        models.put(model.getId(), model);
    }

    public Model get(String id) {
        return models.get(id);
    }

    public void unregister(String id) {
        models.remove(id);
    }

    public void unregister(@NonNull Model model) {
        models.remove(model.getId());
    }

    public Collection<Model> getAll() {
        return models.values();
    }
}
