package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.block.BlockComponentZone;
import xyz.upperlevel.opencraft.world.block.BlockState;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Drawer;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

import java.nio.FloatBuffer;

public class RenderChunkBuffer {

    @Getter
    public final Chunk[][][] data;

    @Getter
    public final int rd; // rendering distance

    /**
     * The side of the rendering area.
     */
    @Getter
    public final int side;

    /**
     * The size of the rendering area.
     */
    @Getter
    public final int size;

    public RenderChunkBuffer(int renderingDistance) {
        rd = renderingDistance;

        side = rd * 2 + 1;
        data = new Chunk[side][side][side];

        size = side * side * side;
    }

    public boolean isOutOfBounds(int x, int y, int z) {
        return (x < 0 || x >= getSide() ||
                y < 0 || y >= getSide() ||
                z < 0 || z >= getSide());
    }

    public Chunk getCenter() {
        return data[rd][rd][rd];
    }

    public Chunk getChunk(int x, int y, int z) {
        return data[x][y][z];
    }

    public void setChunk(int x, int y, int z, Chunk chunk) {
        data[x][y][z] = chunk;
    }

    /**
     * Initializes this ChunkBuffer by picking chunks directly from the world.
     */
    public void setup(Chunk center) {
        int side = getSide();
        for (int x = 0; x < side; x++)
            for (int y = 0; y < side; y++)
                for (int z = 0; z < side; z++)
                    data[x][y][z] = center.getRelative(
                            x - rd,
                            y - rd,
                            z - rd
                    );
    }

    /**
     * Merges an existing ChunkBuffer with the current one.
     */
    public void merge(RenderChunkBuffer other) {
        // gets the position in which start reading/writing the merging buffer
        int off = rd - other.rd;
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
                        data[x][y][z] = getChunk(
                                x + absOff,
                                y + absOff,
                                z + absOff
                        );
                    }
                    // if the offset is positive it means that this buffer
                    // is bigger than the other one so it adds the offset
                    // to the writing data
                    else {
                        data[x + absOff][y + absOff][z + absOff] =
                                getChunk(x, y, z);
                    }
                }
    }

    /**
     * Translates this buffer of offsets values in base of the center Chunk.
     */
    public void translate(int offX, int offY, int offZ, Chunk center) {
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
                            // gets it from the world and loadEvents it
                            center.getRelative(
                                    x - rd,
                                    y - rd,
                                    z - rd
                            ) :
                            // if the chunk has already been loaded inside the data
                            // just get it
                            getChunk(bax, bay, baz);
                    setChunk(x, y, z, chunk);
                }
    }

    // todo canRender
    public void render(Uniformer uniformer, int v) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        // foreach chunk seen by this rendering distance
        for (int cx = 0; cx < side; cx++) {
            for (int cy = 0; cy < side; cy++) {
                for (int cz = 0; cz < side; cz++) {
                    Chunk chunk = data[cx][cy][cz];
                    // foreach block of the chunk
                    for (int bx = 0; bx < Chunk.WIDTH; bx++) {
                        for (int by = 0; by < Chunk.HEIGHT; by++) {
                            for (int bz = 0; bz < Chunk.LENGTH; bz++) {
                                Matrix4f mat = new Matrix4f();

                                // foreach face of the block (already computed)
                                BlockState state = chunk.blocks[bx][by][bz].getState();
                                state.getFaceBuffer().computeFaces();

                                state.getFaceBuffer().getFaces().forEach(face -> {
                                    BlockComponentZone zone = face.getZone();

                                    mat.translate(new Vector3f(-1f)
                                            .add(zone.getSize())
                                            .add(zone.getMinPosition().mul(2))
                                            .mul(1f, 1f, -1f)
                                    );
                                    // translates faces in order to make a cube
                                    mat.translate(face.getPosition().getMod().mul(zone.getSize()));
                                    mat.scale(zone.getSize());

                                    face.getPosition().rotateToCubeRotation(mat);

                                    uniformer.setUniformMatrix("model", mat.get(buf));

                                    Drawer.drawArrays(DrawMode.TRIANGLES, 0, v);
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}