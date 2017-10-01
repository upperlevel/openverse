package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.util.math.Aabb2f;
import xyz.upperlevel.openverse.util.math.Aabb3d;
import xyz.upperlevel.openverse.util.math.Aabb3f;

@Getter
@RequiredArgsConstructor
public enum Facing {
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    FRONT(0, 0, -1),
    BACK(0, 0, 1),
    RIGHT(1, 0, 0),
    LEFT(-1, 0, 0);

    public final int offsetX, offsetY, offsetZ;

    /**
     * Resolves the {@link Aabb2f} from the given {@link Aabb3f}.
     * @param aabb the {@link Aabb3f} on which retrieve the {@link Aabb2f}
     * @return the {@link Aabb2f}
     */
    public Aabb2f resolveAabb(Aabb3f aabb) {
        switch (this) {
            case UP:
            case DOWN:
                return new Aabb2f(aabb.minX, aabb.minZ, aabb.maxX, aabb.maxZ);
            case FRONT:
            case BACK:
                return new Aabb2f(aabb.minX, aabb.minY, aabb.maxX, aabb.maxY);
            case RIGHT:
            case LEFT:
                return new Aabb2f(aabb.minY, aabb.minZ, aabb.maxY, aabb.maxZ);
            default:
                return null;
        }
    }

    /**
     * Gets the opposite {@link Facing} to this face.
     * @return the opposite {@link Facing} to this face
     */
    public Facing getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case FRONT:
                return BACK;
            case BACK:
                return FRONT;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            default:
                return null;
        }
    }
}
