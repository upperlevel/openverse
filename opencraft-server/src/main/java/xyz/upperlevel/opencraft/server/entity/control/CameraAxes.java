package xyz.upperlevel.opencraft.server.entity.control;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CameraAxes implements Axes {

    public CameraAxes() {
    }

    public Matrix4f getOrientation(float yaw, float pitch) {
        Matrix4f r = new Matrix4f();
        r.rotate((float) Math.toRadians(pitch), new Vector3f(1f, 0, 0));
        r.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1f, 0));
        return r;
    }

    @Override
    public Vector3f getRight(float yaw, float pitch) {
        return getOrientation(yaw, pitch).invert(new Matrix4f()).transformDirection(new Vector3f(1f, 0, 0));
    }

    @Override
    public Vector3f getUp(float yaw, float pitch) {
        return  getOrientation(yaw, pitch).invert(new Matrix4f()).transformDirection(new Vector3f(0, 1f, 0));
    }

    @Override
    public Vector3f getForward(float yaw, float pitch) {
        return getOrientation(yaw, pitch).invert(new Matrix4f()).transformDirection(new Vector3f(0, 0, -1f));
    }
}