package xyz.upperlevel.opencraft.world.block;

import xyz.upperlevel.opencraft.world.Block;

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

    public Optional<BlockComponent> getComponent(String id) {
        return components.stream()
                .filter(comp -> comp.getId().equals(id))
                .findAny();
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

    @SuppressWarnings("unchecked")
    public void load(Map<String, Object> data) {
        for (Map<String, Object> subData : (List<Map<String, Object>>) data.get("components")) {
            BlockComponent component = new BlockComponent((String) subData.get("id")); // can be null
            component.load(subData);
            components.add(component);
        }
    }

    public Map<String, Object> save() {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> compData = new ArrayList<>();
        components.forEach(comp -> compData.add(comp.save()));
        data.put("components", compData);
        return data;
    }

    public BlockShape copy() {
        return new BlockShape().addComponents(components.stream()
                .map(BlockComponent::copy)
                .collect(Collectors.toList()));
    }

    public static BlockShape empty() {
        return new BlockShape();
    }

    private final FaceBuffer faceBuf = new FaceBuffer();

    public FaceBuffer getFaceBuffer() {
        return faceBuf;
    }

    public class FaceBuffer { // useful for rendering

        private List<BlockFace> faces = new ArrayList<>();

        private FaceBuffer() {
        }

        public List<BlockFace> computeFaces() {
            return computeFaces(BlockComponent.NULL);
        }

        public List<BlockFace> computeFaces(BlockComponent component) {
            // foreach component compute its faces
            components.forEach(comp -> faces.addAll(comp.getFaceBuffer().computeFaces(component)));
            return faces;
        }

        public List<BlockFace> getFaces() {
            return faces;
        }
    }
}
