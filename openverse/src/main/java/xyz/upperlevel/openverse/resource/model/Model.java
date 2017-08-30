package xyz.upperlevel.openverse.resource.model;

import lombok.Getter;
import org.joml.Vector3d;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.resource.model.shape.Shape;
import xyz.upperlevel.openverse.resource.model.shape.ShapeType;
import xyz.upperlevel.openverse.util.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <S> {@link Shape} objects have a different implementation in client-side.
 */
@Getter
public class Model<S extends Shape> {
    private final List<S> shapes = new ArrayList<>();

    public void addShape(S shape) {
        shapes.add(shape);
    }

    // todo re-do (with buffering)!
    public Box getBox() {
        Vector3d min = null,
                max = null;

        for (Shape shape : shapes) {
            Box box = shape.getBox();
            double x, y, z;

            // checks if current box min pos is lower than cached
            x = box.minX;
            y = box.minY;
            z = box.minZ;

            if (min == null) {
                min = new Vector3d(x, y, z);
            } else {
                if (x < min.x)
                    min.x = x;
                if (y < min.y)
                    min.y = y;
                if (z < min.z)
                    min.z = z;
            }

            // checks if current box max pos is higher than cached
            x = box.maxX;
            y = box.maxY;
            z = box.maxZ;

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
                max.x,
                max.y,
                max.z
        );
    }

    @SuppressWarnings("unchecked")
    public static <M extends Model> M deserialize(Config config) {
        Model<Shape> res = new Model<>();
        for (Config subCfg : config.getConfigList("shapes")) {
            ShapeType shapeFac = Openverse.resources().shapes().entry(subCfg.getStringRequired("type"));
            if (shapeFac != null)
                res.addShape(shapeFac.create(subCfg));
        }
        return (M) res; // we are sure that this is a Model
    }
}