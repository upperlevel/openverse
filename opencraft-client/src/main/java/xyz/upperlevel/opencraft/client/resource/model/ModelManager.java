package xyz.upperlevel.opencraft.client.resource.model;


import lombok.NonNull;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModelManager {

    private Map<String, BlockShape> shapes = new HashMap<>();

    public ModelManager() {
    }

    public void register(BlockShape shape) {
        shapes.put(shape.getId(), shape);
    }

    public BlockShape get(String id) {
        return shapes.get(id);
    }

    public void unregister(String id) {
        shapes.remove(id);
    }

    public void unregister(@NonNull BlockShape shape) {
        shapes.remove(shape.getId());
    }

    public Collection<BlockShape> getShapes() {
        return shapes.values();
    }
}
