package world;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public enum BlockFacePosition {

    UP(0, 1, 0) {
        @Override
        public Matrix4f rotateToCubeRotation(Matrix4f matrix) {
            return BlockFacePosition.rotateToCubeRotation(matrix, -1f, 0f, 0f);
        }
    },
    DOWN(0, -1, 0) {
        @Override
        public Matrix4f rotateToCubeRotation(Matrix4f matrix) {
            return BlockFacePosition.rotateToCubeRotation(matrix, 1f, 0f, 0f);
        }
    },
    LEFT(-1, 0, 0) {
        @Override
        public Matrix4f rotateToCubeRotation(Matrix4f matrix) {
            return BlockFacePosition.rotateToCubeRotation(matrix, 0f, -1f, 0f);
        }
    },
    RIGHT(1, 0, 0) {
        @Override
        public Matrix4f rotateToCubeRotation(Matrix4f matrix) {
            return BlockFacePosition.rotateToCubeRotation(matrix, 0f, 1f, 0f);
        }
    },
    FORWARD(0, 0, 1) {
        @Override
        public Matrix4f rotateToCubeRotation(Matrix4f matrix) {
            return matrix;
        }
    },
    BACKWARD(0, 0, -1) {
        @Override
        public Matrix4f rotateToCubeRotation(Matrix4f matrix) {
            return matrix.rotate((float) Math.PI, 0f, 1f, 0f);
        }
    };

    @Getter
    public final int directionX, directionY, directionZ;

    BlockFacePosition(int dirX, int dirY, int dirZ) {
        directionX = dirX;
        directionY = dirY;
        directionZ = dirZ;
    }

    public Vector3f getDirection() {
        return new Vector3f(directionX, directionY, directionZ);
    }

    public Zone3f obtainZone(Zone3f zone) {
        return new Zone3f(
                directionX > 0 ? zone.maxX : zone.minX,
                directionY > 0 ? zone.maxY : zone.minY,
                directionZ > 0 ? zone.maxZ : zone.minZ,

                directionX < 0 ? zone.minX : zone.maxX,
                directionY < 0 ? zone.minY : zone.maxY,
                directionZ < 0 ? zone.minZ : zone.maxZ
        );
    }

    public abstract Matrix4f rotateToCubeRotation(Matrix4f matrix);

    private static Matrix4f rotateToCubeRotation(Matrix4f matrix, float x, float y, float z) {
        return matrix.rotate((float) Math.PI / 2, x, y, z);
    }

    public Relative asRelative() {
        return Relative.values()[ordinal()];
    }
}