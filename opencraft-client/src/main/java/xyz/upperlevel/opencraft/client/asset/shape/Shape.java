package xyz.upperlevel.opencraft.client.asset.shape;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shape {

    @Getter
    private String id;

    private Map<String, ShapeComponent> byIdComponents = new HashMap<>();

    @Getter
    private List<ShapeComponent> components = new ArrayList<>();

    public Shape(@NonNull String id) {
        this.id = id;
    }

    public void add(@NonNull ShapeComponent component) {
        if (component.getId() != null)
            byIdComponents.put(component.getId(), component);
        components.add(component);
    }

    public ShapeComponent getComponent(String id) {
        return byIdComponents.get(id);
    }

    public void remove(@NonNull ShapeComponent component) {
        byIdComponents.remove(component.getId());
        components.remove(component);
    }
}
