package xyz.upperlevel.opencraft.client.resource.model;

import lombok.NonNull;
import xyz.upperlevel.opencraft.resource.model.Model;

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

    public ModelManager unregister(String id) {
        models.remove(id);
        return this;
    }

    public ModelManager unregister(@NonNull Model model) {
        models.remove(model.getId());
        return this;
    }

    public Collection<Model> getAll() {
        return models.values();
    }
}
