package xyz.upperlevel.opencraft.server.shape;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.server.physic.collision.Box;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    @Getter
    private String id;

    @Getter
    private Box box = new Box();

    @Getter
    private List<ShapeComponent> comps = new ArrayList<>();

    public Shape(@NonNull String id) {
        this.id = id;
    }

    public void add(ShapeComponent comp) {
        comps.add(comp);

        // updates shape aabb
        Vector3f smn = box.getMin();
        Vector3f cmn = comp.getBox().getMin();

        if (cmn.x < smn.x)
            smn.x = cmn.x;

        if (cmn.y < smn.y)
            smn.y = cmn.y;

        if (cmn.z < smn.z)
            smn.z = cmn.z;

        Vector3f smx = box.getMax();
        Vector3f cmx = comp.getBox().getMax();

        if (cmx.x > smx.x)
            smx.x = cmx.x;

        if (cmx.y > smx.y)
            smx.y = cmx.y;

        if (cmx.z > smx.z)
            smx.z = cmx.z;
    }
}
