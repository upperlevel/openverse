package xyz.upperlevel.openverse.resource.model.impl;

import lombok.Getter;
import org.joml.Vector3i;
import xyz.upperlevel.openverse.physic.Box;

public enum CubeFacePosition {

    UP(0, 1, 0) {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y + cube.height, cube.z, cube.width, 0, cube.depth);
        }
    },
    DOWN(0, -1, 0) {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z, cube.width, 0, cube.depth);
        }
    },

    RIGHT(1, 0, 0) {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x + cube.width, cube.y, cube.z, 0, cube.height, cube.depth);
        }
    },
    LEFT(-1, 0, 0) {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z, 0, cube.height, cube.depth);
        }
    },

    FRONT(0, 0, 1) {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z + cube.depth, cube.width, cube.height, 0);
        }
    },
    BACK(0, 0, -1) {
        @Override
        public Box getBox(Box cube) {
            return new Box(cube.x, cube.y, cube.z, cube.width, cube.height, 0);
        }
    };

    @Getter
    public final int dirX, dirY, dirZ;

    CubeFacePosition(int dx, int dy, int dz) {
        dirX = dx;
        dirY = dy;
        dirZ = dz;
    }

    public Vector3i getDir() {
        return new Vector3i(dirX, dirY, dirZ);
    }

    public abstract Box getBox(Box cube);
}