package xyz.upperlevel.openverse.client.render.world.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.Iterator;

@Getter
public abstract class ArraySlider3d<T> implements Iterable<T> {
    private T[] data;
    private final int side, size;
    private int x, y, z; // top left coordinates

    @SuppressWarnings("unchecked")
    public ArraySlider3d(int x, int y, int z, int side) {
        this.data = (T[]) new Object[side * side * side];
        this.side = side;
        this.size = side * side;
        this.refreshTo(x, y, z); // to initialize its content
    }

    public ArraySlider3d(int side) {
        this(0, 0, 0, side);
    }

    /**
     * Gets element from relative coordinates.
     */
    public T get(int x, int y, int z) {
        return this.data[x * side * side + y * side + z];
    }

    public void set(int x, int y, int z, T data) {
        this.data[x * side * side + y * side + z] = data;
    }

    /**
     * Asks for a new object at the given coordinates that it hasn't cached yet.
     */
    public abstract T ask(int x, int y, int z);

    /**
     * Slides the current array of offset values.
     */
    public void slide(int offsetX, int offsetY, int offsetZ) {
        for (int ix = 0; ix < side; ix++) {
            for (int iy = 0; iy < side; iy++) {
                for (int iz = 0; iz < side; iz++) {
                    int tx = ix + offsetX;
                    int ty = iy + offsetY;
                    int tz = iz + offsetZ;
                    T trg;
                    if (tx >= side || tx < 0 || ty >= side || ty < 0 || tz >= side || tz < 0) {
                        trg = ask(tx, ty, tz);
                    } else {
                        trg = get(tx, ty, tz);
                    }
                    set(ix, iy, iz, trg);
                }
            }
        }
    }

    /**
     * Slides the current array to another absolute position.
     */
    public void slideTo(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        slide(x - this.x, y - this.y, z - this.z);
    }

    /**
     * Refreshes current position objects.
     */
    public void refresh() {
        refreshTo(x, y, z);
    }

    /**
     * Moves to given absolute position and refreshes objects.
     */
    public void refreshTo(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        for (int ix = 0; ix < side; ix++)
            for (int iy = 0; iy < side; iy++)
                for (int iz = 0; iz < side; iz++)
                    set(ix, iy, iz, ask(x + ix, y + iy, z + iz));
    }

    /**
     * Clears all objects.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        data = (T[]) new Object[data.length];
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < data.length; i++)
            r.append(i).append(". ").append(data[i]).append("\n");
        return r.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(data).iterator();
    }
}
