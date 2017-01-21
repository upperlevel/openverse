package xyz.upperlevel.opencraft.world.block.shape;

import java.util.*;
import java.util.stream.Collectors;

public class BlockShape {

    private final List<BlockComponent> components = new ArrayList<>();

    public BlockShape() {
    }

    public BlockShape addComponent(BlockComponent component) {
        components.add(component);
        return this;
    }

    public BlockShape addComponents(Collection<BlockComponent> components) {
        this.components.addAll(components);
        return this;
    }

    public BlockShape removeComponent(BlockComponent component) {
        components.remove(component);
        return this;
    }

    public BlockShape removeComponents(Collection<BlockComponent> components) {
        this.components.removeAll(components);
        return this;
    }

    public List<BlockComponent> getComponents() {
        return components;
    }

    public void load(Map<String, Object> data) {
        data.get("components");
    }

    public Map<String, Object> save() {
        Map<String, Object> data = new HashMap<>();
    }

    public BlockShape copy() {
        return new BlockShape().addComponents(components.stream()
                .map(BlockComponent::copy)
                .collect(Collectors.toList()));
    }

    public static BlockShape empty() {
        return new BlockShape();
    }
}
