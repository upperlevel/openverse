package xyz.upperlevel.openverse.resource.model.impl;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3d;
import xyz.upperlevel.openverse.physic.Box;

import java.util.ArrayList;
import java.util.List;

public class NodeModel<P extends ModelPart> {

    @Getter
    private final String id;

    @Getter
    private final List<P> parts = new ArrayList<>();

    public NodeModel(@NonNull String id) {
        this.id = id;
    }

    public NodeModel add(P part) {
        parts.add(part);
        return this;
    }

    public boolean remove(P part) {
        return parts.remove(part);
    }

    public void clear() {
        parts.clear();
    }

    public Box getBox() {
        Vector3d min = null,
                max = null;

        for (ModelPart part : parts) {
            Box box = part.getBox();
            double x, y, z;

            // checks if current box min pos is lower than cached
            x = box.minX();
            y = box.minY();
            z = box.minZ();

            if (min == null) {
                min = new Vector3d();
            } else {
                if (x < min.x)
                    min.x = x;
                if (y < min.y)
                    min.y = y;
                if (z < min.z)
                    min.z = z;
            }

            // checks if current box max pos is higher than cached
            x = box.maxX();
            y = box.maxY();
            z = box.maxZ();

            if (max == null) {
                max = new Vector3d(x, y, z);
            } else {
                if (x > max.x)
                    max.x = x;
                if (y > max.y)
                    max.y = y;
                if (z > max.z)
                    max.z = z;
            }
        }

        // if the two position hasn't been init yet
        if (min == null)
            min = new Vector3d();
        if (max == null)
            max = new Vector3d();

        return new Box(
                min.x,
                min.y,
                min.z,
                max.x - min.x,
                max.y - min.y,
                max.z - min.z
        );
    }
}