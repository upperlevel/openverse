package xyz.upperlevel.opencraft.world.block;

import lombok.*;
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
}