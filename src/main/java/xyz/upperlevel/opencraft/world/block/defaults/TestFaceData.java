package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.graphicengine.api.opengl.shader.Program;
import xyz.upperlevel.graphicengine.api.opengl.shader.ShaderType;
import xyz.upperlevel.graphicengine.api.opengl.shader.ShaderUtil;
import xyz.upperlevel.graphicengine.api.opengl.shader.Uniformer;
import xyz.upperlevel.graphicengine.api.util.Color;
import xyz.upperlevel.graphicengine.api.util.Colors;
import xyz.upperlevel.opencraft.world.block.FaceData;

import java.util.Objects;

public class TestFaceData extends FaceData {

    public static final Program PROGRAM = new Program();

    static {
        ClassLoader classLoader = TestFaceData.class.getClassLoader();
        PROGRAM.attach(ShaderUtil.load(ShaderType.VERTEX, classLoader.getResourceAsStream("resources/vertex.glsl")));
        PROGRAM.attach(ShaderUtil.load(ShaderType.FRAGMENT, classLoader.getResourceAsStream("resources/fragment.glsl")));
        PROGRAM.link();
    }

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
    public void render() {
        Uniformer uniformer = PROGRAM.bind();
        uniformer.setUniform("color", color);
    }
}