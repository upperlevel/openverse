package xyz.upperlevel.opencraft.client.resource.model;

import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

public enum CubeFacePosition {

    UP(0, 1, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), -1f, 0f, 0f);
        }

        @Override
        public Box getBox(Box box) {
            return new Box(box.x, box.y + box.height, box.z, box.width, 0, box.depth);
        }
    },
    DOWN(0, -1, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), 1f, 0f, 0f);
        }

        @Override
        public Box getBox(Box box) {
            return new Box(box.x, box.y, box.z, box.width, 0, box.depth);
        }
    },
    LEFT(-1, 0, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), 0f, -1f, 0f);
        }

        @Override
        public Box getBox(Box box) {
            return new Box(box.x, box.y, box.z, 0, box.height, box.depth);
        }
    },
    RIGHT(1, 0, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), 0f, 1f, 0f);
        }

        @Override
        public Box getBox(Box box) {
            return new Box(box.x + box.width, box.y, box.z, 0, box.height, box.depth);
        }
    },
    FORWARD(0, 0, 1) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f();
        }

        @Override
        public Box getBox(Box box) {
            return new Box(box.x, box.y, box.z + box.depth, box.width, box.height, 0);
        }
    },
    BACKWARD(0, 0, -1) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) Math.PI, 0f, 1f, 0f);
        }

        @Override
        public Box getBox(Box box) {
            return new Box(box.x, box.y, box.z, box.width, box.height, 0);
        }
    };

    private int dx, dy, dz;

    CubeFacePosition(int dirX, int dirY, int dirZ) {
        dx = dirX;
        dy = dirY;
        dz = dirZ;
    }

    public Vector3f getDirection() {
        return new Vector3f(dx, dy, dz);
    }

    public abstract AxisAngle4f getRotation();

    public abstract Box getBox(Box box);
}