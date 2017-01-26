package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.opencraft.world.block.BlockComponent;
import xyz.upperlevel.opencraft.world.block.FaceData;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.ulge.util.Colors;

import java.util.Objects;

public class TestFaceData extends FaceData {

    private Color color = Colors.WHITE;

    protected TestFaceData() {
        super("test_face_data");
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        Objects.requireNonNull(color, "Test block face cannot be null");
        this.color = color;
    }

    @Override
    public void render(Uniformer uniformer) {
        uniformer.setUniform("color", color);
    }
}