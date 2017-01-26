package xyz.upperlevel.opencraft.world.block;

import org.joml.Vector3f;

public class BlockComponentZone {

    private Vector3f
            firstPosition = new Vector3f(),
            secondPosition = new Vector3f();

    public BlockComponentZone() {
    }

    public BlockComponentZone(float pos1x, float pos1y, float pos1z, float pos2x, float pos2y, float pos2z) {
        this(new Vector3f(pos1x, pos1y, pos1z), new Vector3f(pos2x, pos2y, pos2z));
    }

    public BlockComponentZone(Vector3f firstPosition, Vector3f secondPosition) {
        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
    }

    public float getWidth() {
        return getMaxX() - getMinX();
    }

    public float getHeight() {
        return getMaxY() - getMinY();
    }

    public float getLength() {
        return getMaxZ() - getMinZ();
    }

    public Vector3f getSize() {
        return new Vector3f(
                getWidth(),
                getHeight(),
                getLength()
        );
    }

    public Vector3f getMinPosition() {
        return new Vector3f(
                getMinX(),
                getMinY(),
                getMinZ()
        );
    }

    public Vector3f getMaxPosition() {
        return new Vector3f(
                getMaxX(),
                getMaxY(),
                getMaxZ()
        );
    }

    public Vector3f getFirstPosition() {
        return new Vector3f(firstPosition);
    }

    public Vector3f getSecondPosition() {
        return new Vector3f(secondPosition);
    }

    public boolean is1D() {
        return (firstPosition.x == secondPosition.x &&
                firstPosition.y == secondPosition.y &&
                firstPosition.z == secondPosition.z);
    }

    public boolean is2D() {
        return !is1D() &&
                firstPosition.x == secondPosition.x ||
                firstPosition.y == secondPosition.y ||
                firstPosition.z == secondPosition.z;
    }

    public boolean is3D() {
        return !is2D();
    }

    public float getMinX() {
        return Math.min(firstPosition.x, secondPosition.x);
    }

    public float getMaxX() {
        return Math.max(firstPosition.x, secondPosition.x);
    }

    public float getMinY() {
        return Math.min(firstPosition.y, secondPosition.y);
    }

    public float getMaxY() {
        return Math.max(firstPosition.y, secondPosition.y);
    }

    public float getMinZ() {
        return Math.min(firstPosition.z, secondPosition.z);
    }

    public float getMaxZ() {
        return Math.max(firstPosition.z, secondPosition.z);
    }

    public boolean isEqual(BlockComponentZone zone) {
        return (getMinPosition().equals(zone.getMinPosition()) &&
                getMaxPosition().equals(zone.getMaxPosition()));
    }

    /**
     * Checks if the zone passed correspond to a face of this zone.
     */
    public boolean isFace(BlockComponentZone testFace) {
        for (BlockFacePosition position : BlockFacePosition.values()) {
            if (position.getZone(this).isEqual(testFace))
                return true;
        }
        return false;
    }

    public boolean isInside(Vector3f point) {
        return (point.x >= getMinX() && point.x <= getMaxX() &&
                point.y >= getMinY() && point.y <= getMaxY() &&
                point.z >= getMinZ() && point.z <= getMaxZ());
    }

    public boolean isInside(BlockComponentZone zone) {
        return isInside(zone.firstPosition) && isInside(zone.secondPosition);
    }

    public BlockComponentZone getMirror() {
        return new BlockComponentZone(
                secondPosition,
                firstPosition
        );
    }

    public BlockComponentZone copy() {
        return new BlockComponentZone(
                new Vector3f(firstPosition),
                new Vector3f(secondPosition)
        );
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BlockComponentZone && isEqual((BlockComponentZone) object);
    }
}