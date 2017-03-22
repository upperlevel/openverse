package xyz.upperlevel.opencraft.client.physic.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.floor;

// http://playtechs.blogspot.it/2007/03/raytracing-on-grid.html

public class RaycastIterator implements Iterator<PhysicalBlock> {

    private int x, y, z;

    private double dt_dx, dt_dy, dt_dz;

    private int n = 1;
    private int x_inc, y_inc, z_inc;
    private double t_next_y, t_next_x, t_next_z;
    private final PhysicalFace faceX, faceY, faceZ;
    private PhysicalFace lastFace = null;

    public RaycastIterator(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        double dx = Math.abs(startX - endX);
        double dy = Math.abs(startY - endY);
        double dz = Math.abs(startZ - endZ);

        x = (int) (floor(startX));
        y = (int) (floor(startY));
        z = (int) (floor(startZ));

        dt_dx = 1.0 / dx;
        dt_dy = 1.0 / dy;
        dt_dz = 1.0 / dz;

        if (dx == 0) {
            x_inc = 0;
            t_next_x = dt_dx; // infinity
            faceX = null;
        } else if (endX > startX) {
            x_inc = 1;
            n += (int) (floor(endX)) - x;
            t_next_x = (floor(startX) + 1 - startX) * dt_dx;
            faceX = PhysicalFace.LEFT;
        } else {
            x_inc = -1;
            n += x - (int) (floor(endX));
            t_next_x = (startX - floor(startX)) * dt_dx;
            faceX = PhysicalFace.RIGHT;
        }

        if (dy == 0) {
            y_inc = 0;
            t_next_y = dt_dy; // infinity
            faceY = null;
        } else if (endY > startY) {
            y_inc = 1;
            n += (int) (floor(startY)) - y;
            t_next_y = (floor(startY) + 1 - startY) * dt_dy;
            faceY = PhysicalFace.DOWN;
        } else {
            y_inc = -1;
            n += y - (int) (floor(endY));
            t_next_y = (startY - floor(startY)) * dt_dy;
            faceY = PhysicalFace.UP;
        }

        if (dz == 0) {
            z_inc = 0;
            t_next_z = dt_dz; // infinity
            faceZ = null;
        } else if (endZ > startZ) {
            z_inc = 1;
            n += (int) (floor(endZ)) - z;
            t_next_z = (floor(startZ) + 1 - startZ) * dt_dz;
            faceZ = PhysicalFace.FRONT;
        } else {
            z_inc = -1;
            n += z - (int) (floor(endZ));
            t_next_z = (startZ - floor(startZ)) * dt_dz;
            faceZ = PhysicalFace.BACK;
        }
    }

    @Override
    public boolean hasNext() {
        return n > 0;
    }

    @Override
    public PhysicalBlock next() {
        if (!hasNext())
            throw new NoSuchElementException();
        PhysicalBlock current = new PhysicalBlock(x, y, z, lastFace);

        if (t_next_y < t_next_x) {
            if (t_next_z < t_next_y) {
                z += z_inc;
                t_next_z += dt_dz;
                lastFace = faceZ;
            } else {
                y += y_inc;
                t_next_y += dt_dy;
                lastFace = faceY;
            }
        } else {
            if (t_next_z < t_next_x) {
                z += z_inc;
                t_next_z += dt_dz;
                lastFace = faceZ;
            } else {
                x += x_inc;
                t_next_x += dt_dx;
                lastFace = faceX;
            }
        }

        n--;
        return current;
    }
}
