package xyz.upperlevel.opencraft.client.asset.old_shape;

import lombok.Getter;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public enum BlockFacePosition {

    UP(0, 1, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), -1f, 0f, 0f);
        }
    },
    DOWN(0, -1, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), 1f, 0f, 0f);
        }
    },
    LEFT(-1, 0, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), 0f, -1f, 0f);
        }
    },
    RIGHT(1, 0, 0) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) (Math.PI / 2), 0f, 1f, 0f);
        }
    },
    FORWARD(0, 0, 1) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f();
        }
    },
    BACKWARD(0, 0, -1) {
        @Override
        public AxisAngle4f getRotation() {
            return new AxisAngle4f((float) Math.PI, 0f, 1f, 0f);
        }
    };

    @Getter
    private int directionX, directionY, directionZ;

    BlockFacePosition(int dirX, int dirY, int dirZ) {
        directionX = dirX;
        directionY = dirY;
        directionZ = dirZ;
    }

    public abstract AxisAngle4f getRotation();

    public Vector3f getDirection() {
        return new Vector3f(directionX, directionY, directionZ);
    }

    public Zone3f getZone(Zone3f zone) {
        return new Zone3f(
                directionX > 0 ? zone.getMaxX() : zone.getMinX(),
                directionY > 0 ? zone.getMaxY() : zone.getMinY(),
                directionZ > 0 ? zone.getMaxZ() : zone.getMinZ(),

                directionX < 0 ? zone.getMinX() : zone.getMaxX(),
                directionY < 0 ? zone.getMinY() : zone.getMaxY(),
                directionZ < 0 ? zone.getMinZ() : zone.getMaxZ()
        );
    }

    public Zone3f getMirrorZone(Zone3f zone) {
        //System.out.println("face at " + name() + " mirror values:\n" + (directionX != 0) + " " + (directionY != 0) + " " + (directionZ != 0));
        return zone.mirror(directionX != 0, directionY != 0, directionZ != 0);
    }
}