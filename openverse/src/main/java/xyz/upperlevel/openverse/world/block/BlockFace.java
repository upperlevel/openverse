package xyz.upperlevel.openverse.world.block;

public enum BlockFace {
    UP {
        @Override
        public BlockFace opposite() {
            return DOWN;
        }
    },
    DOWN {
        @Override
        public BlockFace opposite() {
            return UP;
        }
    },
    FRONT {
        @Override
        public BlockFace opposite() {
            return BACK;
        }
    },
    BACK {
        @Override
        public BlockFace opposite() {
            return FRONT;
        }
    },
    LEFT {
        @Override
        public BlockFace opposite() {
            return RIGHT;
        }
    },
    RIGHT {
        @Override
        public BlockFace opposite() {
            return LEFT;
        }
    };

    private static BlockFace[] values = values();

    public abstract BlockFace opposite();

    public byte getId() {
        return (byte) ordinal();
    }

    public static BlockFace fromId(byte id) {
        return values[id];
    }
}
