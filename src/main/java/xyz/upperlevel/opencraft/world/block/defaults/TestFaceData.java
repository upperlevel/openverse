package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.graphicengine.api.util.Color;
import xyz.upperlevel.opencraft.world.block.FaceData;

import java.util.Objects;

public class TestFaceData extends FaceData {

    private Color color;

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
}
