package xyz.upperlevel.opencraft.client.asset.shape;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockShapeManager {

    private Map<String, BlockShape> shapes = new HashMap<>();

    public BlockShapeManager() {
    }

    public BlockShapeManager register(BlockShape shape) {
        shapes.put(shape.getId(), shape);
        return this;
    }

    public BlockShape getShape(String id) {
        return shapes.get(id);
    }

    public BlockShapeManager unregister(String id) {
        shapes.remove(id);
        return this;
    }

    public BlockShapeManager unregister(BlockShape shape) {
        shapes.remove(shape.getId());
        return this;
    }

    public Collection<BlockShape> getShapes() {
        return shapes.values();
    }
}
