package xyz.upperlevel.opencraft.client.block;

import lombok.Getter;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.asset.shape.Zone3f;

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
    public final int directionX, directionY, directionZ;

    BlockFacePosition(int dirX, int dirY, int dirZ) {
        directionX = dirX;
        directionY = dirY;
        directionZ = dirZ;
    }

    public Vector3f getDirection() {
        return new Vector3f(directionX, directionY, directionZ);
    }

    public abstract AxisAngle4f getRotation();

    public Zone3f getMirrorZone(Zone3f zone) {
        //System.out.println("face at " + name() + " mirror values:\n" + (directionX != 0) + " " + (directionY != 0) + " " + (directionZ != 0));
        return zone.mirror(directionX != 0, directionY != 0, directionZ != 0);
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
}