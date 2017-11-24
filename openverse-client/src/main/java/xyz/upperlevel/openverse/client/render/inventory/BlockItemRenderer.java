package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.block.*;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.util.exceptions.NotImplementedException;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

public class BlockItemRenderer implements ItemRenderer {
    private static final Program program;
    private static Uniform cameraLoc;
    private Vao vao;
    private Vbo vbo;
    private int vertices;

    static {
        program = ((ClientResources) Openverse.resources()).programs().entry("simple_shader");
    }

    public BlockItemRenderer(BlockType type) {
        this(type, Facing.FRONT);
    }

    public BlockItemRenderer(BlockType type, Facing displayFace) {
        BlockModel model = BlockTypeModelMapper.model(type.getDefaultState());
        if (model == null) {
            throw new IllegalStateException("Cannot find model for " + type);
        }
        List<BlockPart> parts = model.getBlockParts();
        List<BlockPartFace> faces = parts.stream().map(p -> p.getFaces().get(displayFace)).collect(Collectors.toList());

        ByteBuffer buffer = BufferUtils.createByteBuffer(faces.size() * 4 * 6 * Float.BYTES);
        vertices = 0;
        for (BlockPartFace f : faces) {
            vertices += f.renderOnBuffer(0, 0, 0, buffer);
        }
        buffer.flip();

        vao = new Vao();
        vao.bind();

        vbo = new Vbo();
        vbo.bind();
        new VertexLinker()
                .attrib(program.uniformer.getAttribLocation("position"), 3)
                .attrib(program.uniformer.getAttribLocation("texCoords"), 3)
                .setup();

        vbo.loadData(buffer, VboDataUsage.STATIC_DRAW);

        vbo.unbind();
        vao.unbind();

        if (cameraLoc == null) {
            cameraLoc = program.uniformer.get("camera");
        }
    }

    @Override
    public void renderInSlot(Matrix4f trans, SlotGui slot) {
        program.bind();
        cameraLoc.set(trans);
        vao.bind();
        vao.draw(DrawMode.QUADS, 0, vertices);
    }

    @Override
    public void renderInHand(Matrix4f trans) {
        throw new NotImplementedException();
    }

    public void destroy() {
        vao.destroy();
        vbo.destroy();
    }
}
