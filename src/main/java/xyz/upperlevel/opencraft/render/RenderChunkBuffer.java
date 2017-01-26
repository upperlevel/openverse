package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderChunkBuffer {

    public final Chunk[] data;

    @Getter
    public final int renderDistance;

    public RenderChunkBuffer(int renderDistance) {
        this.renderDistance = renderDistance;
        data = new Chunk[renderDistance];
    }

    public int getId(int x, int y, int z) {
        int side = getSide();
        return x + side * (y + side * z);
    }

    public int getSide() {
        return renderDistance * 2 + 1;
    }

    public boolean isOutOfBounds(int x, int y, int z) {
        return (x < 0 || x >= getSide() ||
                y < 0 || y >= getSide() ||
                z < 0 || z >= getSide());
    }

    public Chunk getChunk(int x, int y, int z) {
        return data[getId(x, y, z)];
    }

    public void setChunk(int x, int y, int z, Chunk chunk) {
        data[getId(x, y, z)] = chunk;
    }

    public void setupBuffer(Chunk center) {
        int side = getSide();
        for (int x = 0; x < side; x++)
            for (int y = 0; y < side; y++)
                for (int z = 0; z < side; z++)
                    setChunk(x, y, z, center.getRelative(
                            x - renderDistance,
                            y - renderDistance,
                            z - renderDistance
                    ));
    }

    public void mergeBuffer(RenderChunkBuffer other) {
        // gets the position in which starting reading/writing the merging buffer
        int off = renderDistance - other.renderDistance;
        if (off == 0)
            return;
        int absOff = Math.abs(off);
        int othSd = other.getSide();
        for (int x = 0; x < othSd; x++)
            for (int y = 0; y < othSd; y++)
                for (int z = 0; z < othSd; z++) {
                    // if the offset is negative it means that this buffer
                    // is smaller than the other one so it adds the offset
                    // to the reading data
                    if (off < 0) {
                        setChunk(x, y, z, getChunk(
                                x + absOff,
                                y + absOff,
                                z + absOff
                        ));
                    }
                    // if the offset is positive it means that this buffer
                    // is bigger than the other one so it adds the offset
                    // to the writing data
                    else {
                        setChunk(
                                x + absOff,
                                y + absOff,
                                z + absOff,
                                getChunk(x, y, z)
                        );
                    }
                }
    }

    public void translateBuffer(int offX, int offY, int offZ, Chunk center) {
        int side = getSide();
        for (int x = 0; x < side; x++)
            for (int y = 0; y < side; y++)
                for (int z = 0; z < side; z++) {
                    // gets coordinates at x y z
                    // bring at <coord>
                    int bax = x + offX;
                    int bay = y + offY;
                    int baz = z + offZ;
                    // gets each chunk at the moved coords
                    Chunk chunk = isOutOfBounds(bax, bay, baz) ?
                            // if the chunk is out of bounds (not loaded yet)
                            // gets it from the world and load it
                            center.getRelative(
                                    x - renderDistance,
                                    y - renderDistance,
                                    z - renderDistance
                            ) :
                            // if the chunk has already been loaded inside the data
                            // just get it
                            getChunk(bax, bay, baz);
                    setChunk(x, y, z, chunk);
                }
    }

    public List<Chunk> getData() {
        return Arrays.asList(data);
    }
}
