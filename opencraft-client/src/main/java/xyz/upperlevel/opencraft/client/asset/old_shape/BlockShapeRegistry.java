package xyz.upperlevel.opencraft.client.asset.old_shape;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockShapeRegistry {

    private Map<String, BlockShape> shapes = new HashMap<>();

    public BlockShapeRegistry() {
    }

    public BlockShapeRegistry register(BlockShape shape) {
        shapes.put(shape.getId(), shape);
        return this;
    }

    public BlockShape getShape(String id) {
        return shapes.get(id);
    }

    public BlockShapeRegistry unregister(String id) {
        shapes.remove(id);
        return this;
    }

    public BlockShapeRegistry unregister(BlockShape shape) {
        shapes.remove(shape.getId());
        return this;
    }

    public Collection<BlockShape> getShapes() {
        return shapes.values();
    }
}
