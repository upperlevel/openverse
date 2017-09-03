package xyz.upperlevel.openverse.client.resource.model.shape;

import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.physic.Box;

public enum CubeFacePosition {
    TOP {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.minX, cube.maxY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
        }

        @Override
        public Vector3f getDirection() {
            return new Vector3f(0, 1, 0);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2.), -1f, 0f, 0f);
        }
    },
    BOTTOM {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.minY, cube.maxZ);
        }

        @Override
        public Vector3f getDirection() {
            return new Vector3f(0, -1, 0);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2.), 1f, 0f, 0f);
        }
    },

    RIGHT {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.maxX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
        }

        @Override
        public Vector3f getDirection() {
            return new Vector3f(1, 0, 0);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2.), 0f, 1f, 0f);
        }
    },
    LEFT {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.minX, cube.minY, cube.minZ, cube.minX, cube.maxY, cube.maxZ);
        }

        @Override
        public Vector3f getDirection() {
            return new Vector3f(-1, 0, 0);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2.), 0f, -1f, 0f);
        }
    },

    FRONT {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.minX, cube.minY, cube.maxZ, cube.maxX, cube.maxY, cube.maxZ);
        }

        @Override
        public Vector3f getDirection() {
            return new Vector3f(0, 0, 1);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f();
        }
    },
    BACK {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.minX, cube.minY, cube.minZ, cube.maxX, cube.maxY, cube.maxZ);
        }

        @Override
        public Vector3f getDirection() {
            return new Vector3f(0, 0, -1);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) Math.PI, 0f, 1f, 0f);
        }
    };

    public abstract Box getBox(Box cube);

    public abstract Vector3f getDirection();

    public abstract AxisAngle4f getRotation();
}