package xyz.upperlevel.opencraft.world.block.face;

import org.joml.Matrix4f;

public enum BlockFace {

    UP {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return matrix;
        }
    },
    DOWN {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return rotateMatrix(matrix.translate(-0f, -.5f, 0f), 1f, 0f, 0f);
        }
    },
    LEFT {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return null;
        }
    },
    RIGHT {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return null;
        }
    },
    FORWARD {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return null;
        }
    },
    BACKWARD {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return null;
        }
    };

    public abstract Matrix4f transformMatrix(Matrix4f matrix);

    private static Matrix4f rotateMatrix(Matrix4f matrix, float x, float y, float z) {
        return matrix.rotate((float)(Math.PI / 2.), x, y, z);
    }
}
