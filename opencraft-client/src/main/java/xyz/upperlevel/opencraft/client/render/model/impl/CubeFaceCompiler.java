package xyz.upperlevel.opencraft.client.render.model.impl;

import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.resource.model.impl.CubeFacePosition;

public enum CubeFaceCompiler {

    UP {
        @Override
        public Vector3f getDir() {
            return new Vector3f(0, 1, 0);
        }

        @Override
        public AxisAngle4f getRot() {
            return new AxisAngle4f((float) (Math.PI / 2.), -1f, 0f, 0f);
        }
    },
    DOWN {
        @Override
        public Vector3f getDir() {
            return new Vector3f(0, -1, 0);
        }

        @Override
        public AxisAngle4f getRot() {
            return new AxisAngle4f((float) (Math.PI / 2.), 1f, 0f, 0f);
        }
    },

    RIGHT {
        @Override
        public Vector3f getDir() {
            return new Vector3f(1, 0, 0);
        }

        @Override
        public AxisAngle4f getRot() {
            return new AxisAngle4f((float) (Math.PI / 2.), 0f, 1f, 0f);
        }
    },
    LEFT {
        @Override
        public Vector3f getDir() {
            return new Vector3f(-1, 0, 0);
        }

        @Override
        public AxisAngle4f getRot() {
            return new AxisAngle4f((float) (Math.PI / 2.), 0f, -1f, 0f);
        }
    },

    FRONT {
        @Override
        public Vector3f getDir() {
            return new Vector3f(0, 0, 1);
        }

        @Override
        public AxisAngle4f getRot() {
            return new AxisAngle4f();
        }
    },
    BACK {
        @Override
        public Vector3f getDir() {
            return new Vector3f(0, 0, -1);
        }

        @Override
        public AxisAngle4f getRot() {
            return new AxisAngle4f((float) Math.PI, 0f, 1f, 0f);
        }
    };

    CubeFaceCompiler() {
    }

    public abstract Vector3f getDir();

    public abstract AxisAngle4f getRot();

    public static CubeFaceCompiler from(CubeFacePosition pos) {
        return values()[pos.ordinal()];
    }
}
