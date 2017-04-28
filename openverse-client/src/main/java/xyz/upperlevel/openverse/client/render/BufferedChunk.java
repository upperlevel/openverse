package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.client.resource.TextureBakery;
import xyz.upperlevel.openverse.client.resource.model.ClientModel;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkData;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;
import xyz.upperlevel.ulge.opengl.buffer.VboDataUsage;
import xyz.upperlevel.ulge.opengl.buffer.VertexLinker;

import java.nio.ByteBuffer;


public class BufferedChunk extends Chunk {

    @Getter
    private int allocVertCount = 0, allocDataCount = 0, drawVertCount = 0;

    @Getter
    private Vbo vbo = new Vbo();

    public BufferedChunk(BufferedChunkData data, ClientWorld world, int x, int y, int z) {
        super(data, world, x, y, z);
    }

    public BufferedChunk(ClientWorld world, int x, int y, int z) {
        super(null, world, x, y, z);
        super.data = new BufferedChunkData();//I'm detesting java
    }

    @Override
    public BufferedChunkData getData() {
        return (BufferedChunkData) super.getData();
    }

    public void build() {
        // todo get texture bakery
        TextureBakery bakery = null;

        // initializes a byte-buffer with max dimensions it can assume
        ByteBuffer buffer = BufferUtils.createByteBuffer(allocDataCount * Float.BYTES);
        drawVertCount = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < LENGTH; z++) {
                    Matrix4f in = new Matrix4f()
                            .translate(x, y, z);

                    BlockType block = data.getType(x, y, z);
                    if (block != null) {
                        ClientModel mdl = (ClientModel) block.getModel();
                        if (mdl != null)
                            drawVertCount += mdl.compile(bakery, in, buffer);
                    }
                }
            }
        }
        vbo.loadData(buffer, VboDataUsage.STATIC_DRAW);
    }

    private static final VertexLinker vertexLinker =new VertexLinker()
            .attrib(0, 3)
            .attrib(1, 4)
            .attrib(2, 3);

    @SuppressWarnings("deprecation")
    public void render() {
        vertexLinker.setup();
        vbo.draw(DrawMode.QUADS, 0, drawVertCount);
    }


    public class BufferedChunkData extends ChunkData {

        // fix cannot init an array with generics
        // blocks types here contained must have a ClientModel as model without having to cast it
        private BlockType[][][] types = new BlockType[WIDTH][HEIGHT][LENGTH];

        public BufferedChunkData() {
        }

        @Override
        public BlockType getType(int x, int y, int z) {
            return types[x][y][z];
        }

        @Override
        public void setType(int x, int y, int z, BlockType type) {
            setType(x, y, z, type, true);
        }

        public void setType(int x, int y, int z, BlockType<ClientModel> type, boolean update) {
            ClientModel old_mdl = types[x][y][z] != null ? (ClientModel) types[x][y][z].getModel() : null;
            ClientModel new_mdl = type != null ? type.getModel() : null;

            types[x][y][z] = type;

            // gets vertices/data count for old and new model
            int old_vrt = old_mdl == null ? 0 : old_mdl.getVerticesCount();
            int new_vrt = new_mdl == null ? 0 : new_mdl.getVerticesCount();

            int old_data = old_mdl == null ? 0 : old_mdl.getDataCount();
            int new_data = new_mdl == null ? 0 : new_mdl.getDataCount();

            // updates vertices/data count
            allocVertCount += new_vrt  - old_vrt;
            allocDataCount += new_data - old_data;

            // rebuilds chunk if requested
            if (update)
                build();
        }
    }
}
