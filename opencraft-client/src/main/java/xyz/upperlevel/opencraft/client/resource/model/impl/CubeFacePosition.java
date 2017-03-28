package xyz.upperlevel.opencraft.client.resource.model.impl;

import xyz.upperlevel.opencraft.common.physic.collision.Box;

public enum CubeFacePosition {

    UP {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y + c.height, c.z, c.width, 0, c.depth);
        }
    },
    DOWN {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z, c.width, 0, c.depth);
        }
    },

    RIGHT {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x + c.width, c.y, c.z, 0, c.height, c.depth);
        }
    },
    LEFT {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z, 0, c.height, c.depth);
        }
    },

    FRONT {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z + c.depth, c.width, c.height, 0);
        }
    },
    BACK {
        @Override
        public Box getBox(Box c) {
            return new Box(c.x, c.y, c.z, c.width, c.height, 0);
        }
    };

    CubeFacePosition() {
    }

    public abstract Box getBox(Box cube);
}