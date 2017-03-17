package xyz.upperlevel.opencraft.client.physic.util;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.physic.util.RaycastBlock.Face;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.floor;

public class RaycastIterator implements Iterator<RaycastBlock> {

    private int rx, ry, rz;
    private final int rex, rey, rez;

    private final int stepX, stepY, stepZ;
    private final double tDeltaX, tDeltaY, tDeltaZ;
    private double tMaxX, tMaxY, tMaxZ;
    private final Face faceX, faceY, faceZ;

    private RaycastBlock next = null;


    private static double forceCeil(double v) {
        return Math.floor(v) + 1;
    }

    public RaycastIterator(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        rx = (int) floor(startX);
        ry = (int) floor(startY);
        rz = (int) floor(startZ);

        rex = (int) floor(endX);
        rey = (int) floor(endY);
        rez = (int) floor(endZ);

        if (rx == rex && ry == rey && rz == rez) {
            next = new RaycastBlock(rx, ry, rz, null);
            stepX = 0;
            stepY = 0;
            stepZ = 0;
            tDeltaX = 0.0;
            tDeltaY = 0.0;
            tDeltaZ = 0.0;
            faceX = null;
            faceY = null;
            faceZ = null;
        } else {
            double dx = endX - startX;
            double dy = endY - startY;
            double dz = endZ - startZ;

            stepX = (int) Math.signum(dx);
            stepY = (int) Math.signum(dy);
            stepZ = (int) Math.signum(dz);

            tMaxX = dx == 0 ? Double.POSITIVE_INFINITY : (forceCeil(startX) - startX) / dx * stepX;
            tMaxY = dy == 0 ? Double.POSITIVE_INFINITY : (forceCeil(startY) - startY) / dy * stepY;
            tMaxZ = dz == 0 ? Double.POSITIVE_INFINITY : (forceCeil(startZ) - startZ) / dz * stepZ;

            tDeltaX = tMaxX * dx * stepX;
            tDeltaY = tMaxY * dy * stepY;
            tDeltaZ = tMaxZ * dz * stepZ;

            faceX = stepX > 0 ? Face.LEFT : Face.RIGHT;
            faceY = stepY > 0 ? Face.DOWN : Face.UP;
            faceZ = stepZ > 0 ? Face.FRONT : Face.BACK;

            calc();
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public RaycastBlock next() {
        if (!hasNext())
            throw new NoSuchElementException();
        RaycastBlock current = next;
        calc();
        return current;
    }

    private void calc() {
        if (rx == rex && ry == rey && rz == rez) {
            next = null;
            return;
        }
        Face face;
        if (tMaxX < tMaxY) {
            if (tMaxX < tMaxZ) {
                tMaxX += tDeltaX;
                rx += stepX;
                face = faceX;
            } else {
                tMaxZ += tDeltaZ;
                rz += stepZ;
                face = faceZ;
            }
        } else {
            if (tMaxY < tMaxZ) {
                tMaxY += tDeltaY;
                ry += stepY;
                face = faceY;
            } else {
                tMaxZ += tDeltaZ;
                rz += stepZ;
                face = faceZ;
            }
        }
        next = new RaycastBlock(rx, ry, rz, face);
    }
}
