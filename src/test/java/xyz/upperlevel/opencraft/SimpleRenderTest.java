package xyz.upperlevel.opencraft;

import org.lwjgl.opengl.GL11;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.VBO;
import xyz.upperlevel.ulge.opengl.buffer.VBODataUsage;
import xyz.upperlevel.ulge.opengl.buffer.VertexLinker;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.opengl.shader.ShaderUtil;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.window.GLFW;
import xyz.upperlevel.ulge.window.Window;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class SimpleRenderTest {

    public static void main(String[] args) {
        Window win = GLFW.createWindow(500, 500, "base opengl canRender", false);
        win.centerPosition();
        win.contextualize();
        win.show();
        win.setVSync(false);

        // creates base program
        Program program = new Program();
        ClassLoader res = SimpleRenderTest.class.getClassLoader();
        program.attach(ShaderUtil.load(ShaderType.VERTEX, res.getResourceAsStream("src/test/resources/vertex.glsl")));
        program.attach(ShaderUtil.load(ShaderType.FRAGMENT, res.getResourceAsStream("src/test/resources/fragment.glsl")));
        // links program
        program.link();
        Uniformer uniformer = program.bind();

        // sets up shape
        float[] vert = {
                -1f, -1f,//0
                1f, -1f,//1
                1f, 1f,//2

                -1f, -1f,//0
                1f, 1f,//2
                -1f, 1f//3
        };
        VBO vbo = new VBO();
        vbo.loadData(vert, VBODataUsage.STATIC_DRAW);
        vbo.bind();
/*
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createBuffer(vert), GL_STATIC_DRAW);
*/
        int posLoc = uniformer.getAttribLocation("position");
        new VertexLinker(DataType.FLOAT)
                .attrib(posLoc, 2)
                .setup();

        while (!win.isClosed()) {
            glClear(GL11.GL_COLOR_BUFFER_BIT);
            glClearColor(0f, 0f, 0f, 0f);

            Drawer.drawArrays(GL11.GL_TRIANGLES, 0, vert.length / 2);

            win.update();
        }
    }
}
