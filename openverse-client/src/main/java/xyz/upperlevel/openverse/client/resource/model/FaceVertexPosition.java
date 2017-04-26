package xyz.upperlevel.openverse.client.resource.model;

public enum FaceVertexPosition {

    TOP_LEFT {
        @Override
        public Vertex create() {
            Vertex v = new Vertex();
            v.setX(0);
            v.setY(1);
            v.setZ(0);

            v.setU(0);
            v.setV(0);
            return v;
        }
    },
    TOP_RIGHT {
        @Override
        public Vertex create() {
            Vertex v = new Vertex();
            v.setX(1);
            v.setY(1);
            v.setZ(0);

            v.setU(1);
            v.setV(0);
            return v;
        }
    },

    BOTTOM_LEFT {
        @Override
        public Vertex create() {
            Vertex v = new Vertex();
            v.setX(0);
            v.setY(0);
            v.setZ(0);

            v.setU(0);
            v.setV(1);
            return v;
        }
    },
    BOTTOM_RIGHT {
        @Override
        public Vertex create() {
            Vertex v = new Vertex();
            v.setX(1);
            v.setY(1);
            v.setZ(0);

            v.setU(1);
            v.setV(1);
            return v;
        }
    };

    FaceVertexPosition() {
    }

    public abstract Vertex create();
}
