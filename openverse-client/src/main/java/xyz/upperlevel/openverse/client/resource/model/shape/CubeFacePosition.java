package xyz.upperlevel.openverse.client.resource.model.shape;

import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.physic.Box;

public enum CubeFacePosition {
    UP() {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y + cube.height, cube.z, cube.width, 0, cube.depth);
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
    DOWN() {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z, cube.width, 0, cube.depth);
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

    RIGHT() {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x + cube.width, cube.y, cube.z, 0, cube.height, cube.depth);
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
    LEFT() {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z, 0, cube.height, cube.depth);
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

    FRONT() {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z + cube.depth, cube.width, cube.height, 0);
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
    BACK() {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z, cube.width, cube.height, 0);
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

    CubeFacePosition() {
    }

    public abstract Box getBox(Box cube);

    public abstract Vector3f getDirection();

    public abstract AxisAngle4f getRotation();
}