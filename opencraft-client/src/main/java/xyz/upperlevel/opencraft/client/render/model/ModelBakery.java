package xyz.upperlevel.opencraft.client.render.model;

import lombok.NonNull;
import xyz.upperlevel.opencraft.common.shape.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelBakerManager {

    private Map<Class<?>, ModelBaker> bakers = new HashMap<>();

    public ModelBakerManager() {
    }

    public void register(ModelBaker comp) {
        bakers.put(comp.getModel(), comp);
    }

    public ModelBaker get(Model m) {
        return bakers.get(m.getClass());
    }

    public void unregister(@NonNull ModelBaker comp) {
        bakers.remove(comp.getModel());
    }

    public Collection<ModelBaker> getAll() {
        return bakers.values();
    }
}
