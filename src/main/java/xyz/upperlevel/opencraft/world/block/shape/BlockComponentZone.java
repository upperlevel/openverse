package xyz.upperlevel.opencraft.world.block.shape;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joml.Vector3f;

@NoArgsConstructor
@AllArgsConstructor
public class BlockComponentZone {

    private Vector3f
            firstPosition = new Vector3f(),
            secondPosition = new Vector3f();

    public Vector3f getFirstPosition() {
        return firstPosition;
    }

    public Vector3f getSecondPosition() {
        return secondPosition;
    }

    public boolean is1D() {
        return  firstPosition.x == secondPosition.x &&
                firstPosition.y == secondPosition.y &&
                firstPosition.z == secondPosition.z;
    }

    public boolean is2D() {
        return  !is1D() && (
                firstPosition.x == secondPosition.x ||
                firstPosition.y == secondPosition.y ||
                firstPosition.z == secondPosition.z
        );
    }

    public boolean is3D() {
        return !is2D();
    }

    public float getMinX() {
        return Math.min(firstPosition.x, secondPosition.y);
    }

    public float getMaxX() {
        return Math.max(firstPosition.x, secondPosition.y);
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

    public boolean isInside(Vector3f point) {
        return point.x >= getMinX() && point.x <= getMaxX() &&
                point.y >= getMinY() && point.y <= getMaxY() &&
                point.z >= getMinZ() && point.z <= getMaxZ();
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
        if (object instanceof BlockComponentZone) {
            BlockComponentZone other = (BlockComponentZone) object;
            return  getMinX() == other.getMinX() &&
                    getMaxX() == other.getMaxX() &&
                    getMinY() == other.getMinY() &&
                    getMaxY() == other.getMaxY() &&
                    getMinZ() == other.getMinZ() &&
                    getMaxZ() == other.getMaxZ();
        }
        return false;
    }
}