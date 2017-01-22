package xyz.upperlevel.opencraft.world.block.defaults;

import xyz.upperlevel.graphicengine.api.opengl.shader.Program;
import xyz.upperlevel.graphicengine.api.opengl.shader.ShaderType;
import xyz.upperlevel.graphicengine.api.opengl.shader.ShaderUtil;
import xyz.upperlevel.graphicengine.api.opengl.shader.Uniformer;
import xyz.upperlevel.opencraft.world.block.FaceDataRenderer;

public class TestFaceDataRenderer implements FaceDataRenderer<TestFaceData> {

    public static final TestFaceDataRenderer $ = new TestFaceDataRenderer();

    public static final Program PROGRAM = new Program();

    static {
        ClassLoader classLoader = TestFaceDataRenderer.class.getClassLoader();
        PROGRAM.attach(ShaderUtil.load(ShaderType.VERTEX, classLoader.getResourceAsStream("resources/vertex.glsl")));
        PROGRAM.attach(ShaderUtil.load(ShaderType.FRAGMENT, classLoader.getResourceAsStream("resources/fragment.glsl")));
        PROGRAM.link();
    }

    private TestFaceDataRenderer() {
    }

    @Override
    public Class<TestFaceData> getFaceDataClass() {
        return TestFaceData.class;
    }

    @Override
    public void render(TestFaceData faceData) {
        Uniformer uniformer = PROGRAM.bind();
        uniformer.setUniform("color", faceData.getColor());
    }

    public static TestFaceDataRenderer $() {
        return $;
    }
}