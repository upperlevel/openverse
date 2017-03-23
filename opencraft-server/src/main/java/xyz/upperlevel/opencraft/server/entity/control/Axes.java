package xyz.upperlevel.opencraft.server.entity.control;

import org.joml.Vector3f;

public interface Axes {

    Vector3f getRight(float yaw, float pitch);

    Vector3f getUp(float yaw, float pitch);

    Vector3f getForward(float yaw, float pitch);

    default Vector3f getLeft(float yaw, float pitch) {
        return getRight(yaw, pitch).mul(-1f);
    }

    default Vector3f getDown(float yaw, float pitch) {
        return getUp(yaw, pitch).mul(-1f);
    }

    default Vector3f getBackward(float yaw, float pitch) {
        return getForward(yaw, pitch).mul(-1f);
    }
}
