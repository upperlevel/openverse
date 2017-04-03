package xyz.upperlevel.opencraft.resource.model.impl;

import lombok.Getter;
import org.joml.Vector3i;
import xyz.upperlevel.opencraft.physic.Box;

public enum CubeFacePosition {

    UP(0, 1, 0) {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y + c.height, c.z, c.width, 0, c.depth);
        }
    },
    DOWN(0, -1, 0) {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z, c.width, 0, c.depth);
        }
    },

    RIGHT(1, 0, 0) {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x + c.width, c.y, c.z, 0, c.height, c.depth);
        }
    },
    LEFT(-1, 0, 0) {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z, 0, c.height, c.depth);
        }
    },

    FRONT(0, 0, 1) {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z + c.depth, c.width, c.height, 0);
        }
    },
    BACK(0, 0, -1) {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z, c.width, c.height, 0);
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