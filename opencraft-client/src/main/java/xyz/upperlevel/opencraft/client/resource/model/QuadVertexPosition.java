package xyz.upperlevel.opencraft.client.resource.model;

public enum QuadVertexPosition {

    TOP_LEFT {
        @Override
        public QuadVertex create() {
            return new QuadVertex()
                    .setX(0)
                    .setY(1)
                    .setZ(0)
                    .setU(0)
                    .setV(0);
        }
    },
    TOP_RIGHT {
        @Override
        public QuadVertex create() {
            return new QuadVertex()
                    .setX(1)
                    .setY(1)
                    .setZ(1)
                    .setU(1)
                    .setV(1);
        }
    },
    BOTTOM_LEFT {
        @Override
        public QuadVertex create() {
            return new QuadVertex()
                    .setX(0)
                    .setY(0)
                    .setZ(0)
                    .setU(0)
                    .setV(1);
        }
    },
    BOTTOM_RIGHT {
        @Override
        public QuadVertex create() {
            return new QuadVertex()
                    .setX(1)
                    .setY(0)
                    .setZ(0)
                    .setU(1)
                    .setV(1);
        }
    };

    public abstract QuadVertex create();
}
