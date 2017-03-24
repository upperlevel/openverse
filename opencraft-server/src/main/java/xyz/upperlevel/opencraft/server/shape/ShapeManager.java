package xyz.upperlevel.opencraft.server.shape;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShapeManager {

    private Map<String, Shape> shapes = new HashMap<>();

    public ShapeManager() {
    }

    public void add(@NonNull Shape shape) {
        shapes.put(shape.getId(), shape);
    }

    public Shape get(String id) {
        return shapes.get(id);
    }

    public Collection<Shape> getAll() {
        return shapes.values();
    }
}