package xyz.upperlevel.opencraft.world.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@RequiredArgsConstructor
public enum BlockFacePosition {

    UP(0, 1, 0) {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return rotateMatrix(matrix.translate(0, 1f, 0), 1f, 0f, 0f);
        }
    },
    DOWN(0, -1, 0) {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return rotateMatrix(matrix.translate(0, -1f, 0), 1f, 0f, 0f);
        }
    },
    LEFT(-1, 0, 0) {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return rotateMatrix(matrix.translate(-1f, 0, 0), 0f, 1f, 0f);
        }
    },
    RIGHT(1, 0, 0) {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return rotateMatrix(matrix.translate(1f, 0, 0), 0f, 1f, 0f);
        }
    },
    FORWARD(0, 0, 1) {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return matrix.translate(0, 0, 1f); // default face position
        }
    },
    BACKWARD(0, 0, -1) {
        @Override
        public Matrix4f transformMatrix(Matrix4f matrix) {
            return matrix.translate(0, 0, -1f);
        }
    };

    @Getter
    public final int directionX, directionY, directionZ;

    public BlockComponentZone getZone(BlockComponentZone zone) {
        return new BlockComponentZone(
                new Vector3f(
                        directionX > 0 ? zone.getMaxX() : zone.getMinX(),
                        directionY > 0 ? zone.getMaxY() : zone.getMinY(),
                        directionZ > 0 ? zone.getMaxZ() : zone.getMinZ()),
                new Vector3f(
                        directionX < 0 ? zone.getMinX() : zone.getMaxX(),
                        directionY < 0 ? zone.getMinY() : zone.getMaxY(),
                        directionZ < 0 ? zone.getMinZ() : zone.getMaxZ())
        );
    }

    public abstract Matrix4f transformMatrix(Matrix4f matrix);

    private static Matrix4f rotateMatrix(Matrix4f matrix, float x, float y, float z) {
        return matrix.rotate((float) (Math.PI / 2.), x, y, z);
    }
}
