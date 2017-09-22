package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.AABBf;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

@Getter
@RequiredArgsConstructor
public enum Facing {
    UP(0, 1, 0, new AxisAngle4f((float) (Math.PI / 2f), -1, 0, 0)),
    DOWN(0, -1, 0, new AxisAngle4f((float) (Math.PI / 2f), 1, 0, 0)),
    FRONT(0, 0, 1, new AxisAngle4f()),
    BACK(0, 0, -1, new AxisAngle4f((float) Math.PI, 0, 1, 0)),
    LEFT(-1, 0, 0, new AxisAngle4f((float) (Math.PI / 2f), 0, -1, 0)),
    RIGHT(1, 0, 0, new AxisAngle4f((float) (Math.PI / 2f), 0, 1, 0));

    public final int dirX, dirY, dirZ;
    public final AxisAngle4f angle;

    public Vector3f getDir() {
        return new Vector3f(dirX, dirY, dirZ);
    }

    public AxisAngle4f getRot() {
        return new AxisAngle4f(angle);
    }

    public AABBf resolveAabb(AABBf aabb) {
        Vector3f v = new Vector3f(
                dirX < 0 ? aabb.minX : aabb.maxX,
                dirY < 0 ? aabb.minY : aabb.maxY,
                dirZ < 0 ? aabb.minZ : aabb.maxZ
        );
        return new AABBf(v, v);
    }

    public AABBf mirrorAabb(AABBf aabb) {
        AABBf mirrored = new AABBf(aabb);
        if (dirX != 0) {
            mirrored.minX = 1f - aabb.minX;
            mirrored.maxX = 1f - aabb.maxX;
        }
        if (dirY != 0) {
            mirrored.minY = 1f - aabb.minY;
            mirrored.maxY = 1f - aabb.maxY;
        }
        if (dirZ != 0) {
            mirrored.minZ = 1f - aabb.minZ;
            mirrored.maxZ = 1f - aabb.maxZ;
        }
        return mirrored;
    }
}
