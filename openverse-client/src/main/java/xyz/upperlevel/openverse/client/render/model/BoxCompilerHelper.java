package xyz.upperlevel.openverse.client.render.model;

import org.joml.Vector3f;
import xyz.upperlevel.openverse.physic.Box;

public final class BoxCompilerHelper {

    private BoxCompilerHelper() {
    }

    public static Vector3f boxPosition(Box box) {
        return new Vector3f(
                (float) box.x,
                (float) box.y,
                (float) box.z
        );
    }
    
    public static Vector3f boxSize(Box box) {
        return new Vector3f(
                (float) box.width,
                (float) box.height,
                (float) box.depth
        );
    }
}
