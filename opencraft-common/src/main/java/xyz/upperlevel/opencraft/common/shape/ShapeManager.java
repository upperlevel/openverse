package xyz.upperlevel.opencraft.common.shape;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShapeManager {

    private Map<String, Model> shapes = new HashMap<>();

    public ShapeManager() {
    }

    public void add(@NonNull Model model) {
        shapes.put(model.getId(), model);
    }

    public Model get(String id) {
        return shapes.get(id);
    }

    public Collection<Model> getAll() {
        return shapes.values();
    }
}