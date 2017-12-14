package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.block.*;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.util.exceptions.NotImplementedException;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.ulge.gui.GuiBounds;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;
import xyz.upperlevel.ulge.window.Window;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

public class BlockItemRenderer implements ItemRenderer {
    private static final Program program;
    private static Uniform boundsLoc;
    private StateRenderData[] renderersByState;

    static {
        program = ((ClientResources) Openverse.resources()).programs().entry("gui_item_shader");
        program.bind();
        boundsLoc = program.uniformer.get("bounds");
        if (boundsLoc == null) throw new IllegalStateException("Cannot find Uniform 'bounds'");
    }

    public BlockItemRenderer(BlockType type) {
        this(type, Facing.FRONT);
    }

    public BlockItemRenderer(BlockType type, Facing displayFace) {
        setupStateRenderers(type, displayFace);
    }

    private void setupStateRenderers(BlockType type, Facing displayFace) {
        List<? extends BlockState> states = type.getStateRegistry().getStates();
        renderersByState = new StateRenderData[states.size()];
        for (int i = 0; i < states.size(); i++) {
            renderersByState[i] = new StateRenderData();
            renderersByState[i].setup(states.get(i), displayFace);
        }
    }

    @Override
    public void renderInSlot(ItemStack item, Window window, GuiBounds bounds, SlotGui slot) {
        program.bind();
        float invWidth = 1f / window.getWidth();
        float invHeight = 1f / window.getHeight();
        boundsLoc.set(
                (float) bounds.minX * invWidth,
                1.0f - (float) bounds.minY * invHeight,         // Invert y
                (float) (bounds.maxX - bounds.minX) * invWidth,    // Convert maxX to width
                (float) (bounds.minY - bounds.maxY) * invHeight     // Convert maxY to height & Invert y: 1 - (max - min) = (min - max)
        );
        TextureBakery.bind();

        StateRenderData data = renderersByState[item.getState()];

        data.vao.bind();
        data.vao.draw(DrawMode.QUADS, 0, data.vertices);
    }

    @Override
    public void renderInHand(ItemStack item, Matrix4f trans) {
        throw new NotImplementedException();
    }

    public void destroy() {
        for (StateRenderData data : renderersByState) {
            data.destroy();
        }
    }

    private static class StateRenderData {
        public Vao vao;
        public Vbo vbo;
        public int vertices;

        public void setup(BlockState state, Facing displayFace) {
            BlockModel model = BlockTypeModelMapper.model(state);
            if (model == null) {
                throw new IllegalStateException("Cannot find model for " + state);
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
        }

        public void destroy() {
            vbo.destroy();
            vao.destroy();
        }
    }
}
