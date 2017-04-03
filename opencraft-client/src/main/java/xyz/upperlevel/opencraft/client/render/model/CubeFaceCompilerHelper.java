package xyz.upperlevel.opencraft.client.render.model;

import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.resource.model.impl.CubeFacePosition;

public enum CubeFaceCompilerHelper {

    UP {
        @Override
        public Vector3f getDirection() {
            return new Vector3f(0, 1, 0);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2.), -1f, 0f, 0f);
        }
    },
    DOWN {
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
        public Vector3f getDirection() {
            return new Vector3f(0, 0, -1);
        }

        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) Math.PI, 0f, 1f, 0f);
        }
    };

    CubeFaceCompilerHelper() {
    }

    public abstract Vector3f getDirection();

    public abstract AxisAngle4f getRotation();

    public static CubeFaceCompilerHelper from(CubeFacePosition pos) {
        return values()[pos.ordinal()];
    }
}
