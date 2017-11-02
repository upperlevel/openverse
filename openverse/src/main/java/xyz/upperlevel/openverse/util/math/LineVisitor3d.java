package xyz.upperlevel.openverse.util.math;

import lombok.Data;
import org.joml.Vector3i;
import xyz.upperlevel.openverse.util.Box;
import xyz.upperlevel.openverse.world.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static xyz.upperlevel.openverse.util.math.MathUtil.floori;

public class LineVisitor3d {

    @SuppressWarnings("Duplicates")
    public static void visitLine(double startX, double startY, double startZ, double endX, double endY, double endZ, LineVisitor visitor) {
        final double dx = abs(endX - startX);
        final double dy = abs(endY - startY);
        final double dz = abs(endZ - startZ);

        int x = floori(startX);
        int y = floori(startY);
        int z = floori(startZ);

        //
        final double invDx = 1.0 / dx;
        final double invDy = 1.0 / dy;
        final double invDz = 1.0 / dz;

        int n = 1;
        final int stepX, stepY, stepZ;
        double nextX, nextY, nextZ;

        final BlockFace faceX, faceY, faceZ;

        if (dx == 0) {// Horizontal
            stepX = 0;
            nextX = Double.POSITIVE_INFINITY;
            faceX = null;// Cannot collide in the x axis
        } else if (endX > startX) {// To the right
            stepX = 1;
            n += floori(endX) - x;
            nextX = (floori(startX) + 1 - startX) * invDx;
            faceX = BlockFace.LEFT;// Always collide in the left face
        } else {// To the left
            stepX = -1;
            n += x - floori(endX);
            nextX = (startX - floori(startX)) * invDx;
            faceX = BlockFace.RIGHT;// Always collide in the right face
        }

        if (dy == 0) {// Vertical
            stepY = 0;
            nextY = Double.POSITIVE_INFINITY; // infinity
            faceY = null;// Cannot collide in the y axis
        } else if (endY > startY) {// Goes up
            stepY = 1;
            n += floori(endY) - y;
            nextY = (floori(startY) + 1 - startY) * invDy;
            faceY = BlockFace.DOWN;// Always colliding in the bottom face
        } else {// Goes down
            stepY = -1;
            n += y - floori(endY);
            nextY = (startY - floori(startY)) * invDy;
            faceY = BlockFace.UP;// Always colliding in the upper face
        }

        if (dz == 0) {// Z axis
            stepZ = 0;
            nextZ = Double.POSITIVE_INFINITY; // infinity
            faceZ = null;// Cannot collide in the z axis
        } else if (endZ > startZ) {// Goes to the back
            stepZ = 1;
            n += floori(endZ) - z;
            nextZ = (floori(startZ) + 1 - startZ) * invDz;
            faceZ = BlockFace.FRONT;// Always colliding in the front face
        } else {// Goes to the front
            stepZ = -1;
            n += z - floori(endZ);
            nextZ = (startZ - floori(startZ)) * invDz;
            faceZ = BlockFace.BACK;// Always colliding in the upper face
        }

        BlockFace face = null;// Note: the first face needs to be null (we don't enter it, we're already inside)

        for (; n > 0; --n) {
            if (!visitor.onVisit(x, y, z, face)) {// exit if onVisit returns false
                return;
            }

            if (nextX < nextY) {
                if(nextX < nextZ) {
                    //Go to the next x block
                    face = faceX;
                    x += stepX;
                    nextX += invDx;
                } else {
                    // Go to the next z block
                    face = faceZ;
                    z += stepZ;
                    nextZ += invDz;
                }
            } else {
                if (nextY < nextZ) {
                    // Go to the next y block
                    face = faceY;
                    y += stepY;
                    nextY += invDy;
                } else {
                    // Go to the next z block
                    face = faceZ;
                    z += stepZ;
                    nextZ += invDz;
                }
            }
        }
    }

    public static List<Vector3i> visitLine(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        List<Vector3i> res = new ArrayList<>();
        visitLine(startX, startY, startZ, endX, endY, endZ, (x, y, z, face) -> {
            res.add(new Vector3i(x, y, z));
            return true;// Continue
        });
        return res;
    }

    public static RayCastResult rayCast(double startX, double startY, double startZ, double endX, double endY, double endZ, VectorPredicate3i predicate) {
        Box<RayCastResult> res = new Box<>();
        visitLine(startX, startY, startZ, endX, endY, endZ, (x, y, z, face) -> {
            if (predicate.test(x, y, z, face)) {
                res.set(new RayCastResult(new Vector3i(x, y, z), face));
                return false;// Stop iteration
            } else return true;// Continue
        });
        return res.get();
    }

    public interface VectorPredicate3i {
        boolean test(int x, int y, int z, BlockFace face);
    }

    public interface LineVisitor {
        boolean onVisit(int x, int y, int z, BlockFace face);
    }

    @Data
    public static class RayCastResult {
        private final Vector3i block;
        private final BlockFace face;
    }
}
