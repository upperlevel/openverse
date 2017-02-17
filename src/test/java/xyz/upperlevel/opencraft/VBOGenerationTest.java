package xyz.upperlevel.opencraft;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import xyz.upperlevel.opencraft.renderer.texture.Textures;
import world.Chunk;
import world.ChunkGenerators;
import world.World;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.opengl.shader.ShaderUtil;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.opengl.texture.Texture2D;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageLoaderManager;
import xyz.upperlevel.ulge.util.math.Camera;
import xyz.upperlevel.ulge.util.math.Rot;
import xyz.upperlevel.ulge.window.GLFW;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.GLFWCursorMoveEventHandler;
import xyz.upperlevel.ulge.window.event.key.Key;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import static java.lang.System.out;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class VBOGenerationTest {

    public static void main(String[] a) {
        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0f,0,1f));
        camera.setRotation(new Rot());
        camera.setNearAndFarPlane(0.01f, 100f);

        Window win = GLFW.createWindow(500, 500, "game canRender", false);
        camera.setAspectRatio(500f / 500f);

        // registers cursor move event
        GLFWCursorMoveEventHandler cmHandler = GLFW.events().CURSOR_MOVE.inst();
        cmHandler.register(new CursorMoveEvent() {
            private double lastX = 0, lastY = 0;

            @Override
            public void onCall(Window window, double x, double y) {
                double movX = x - lastX;
                double movY = y - lastY;

                Rot rotation = camera.getRotation();
                rotation.add(new Rot(Math.toRadians(movX), Math.toRadians(movY), 0));
                //rotation.setPitch(Math.max(-90, Math.min(90, Math.toRadians(movY))));

                lastX = x;
                lastY = y;
            }
        });
        win.registerEventHandler(cmHandler);
        // registers key callback
        win.centerPosition();
        win.setCursorPosition(0, 0);
        win.disableCursor();
        win.contextualize();
        win.show();

        // creates a shader program and loads shader, finally bind it and keep it bound
        Program program = new Program();
        ClassLoader classLoader = VBOGenerationTest.class.getClassLoader();
        program.attach(ShaderUtil.load(ShaderType.VERTEX, classLoader.getResourceAsStream("shaders/vertex.glsl")));
        program.attach(ShaderUtil.load(ShaderType.FRAGMENT, classLoader.getResourceAsStream("shaders/fragment.glsl")));
        program.link();
        Uniformer uniformer = program.bind();

        try {
            Textures.manager().register(ImageIO.read(classLoader.getResourceAsStream("textures/dirt.jpg")));
        } catch (IOException e) {
            throw new IllegalStateException("error in tex loading", e);
        }

        Textures.manager().getOutput().bind();
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        ImageContent content = ImageLoaderManager.DEFAULT.load(new File("C:/users/lorenzo/desktop/hello.png"));
        Texture2D ENABLED_TEX = new Texture2D();
        ENABLED_TEX.loadImage(content);
        ENABLED_TEX.bind();

        int posAtr = uniformer.getAttribLocation("position");
        out.println("pos atr location: " + posAtr);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);

        out.println("Attempting to createVertex VBO...");
        World w = new World(ChunkGenerators.FLAT);
        Chunk chunk = w.getChunk(0, 0, 0);
        chunk.load();

        long startAt  = System.currentTimeMillis();
        VertexBuffer buffer = new VertexBuffer(1024 * 1024);
        int VERT_COUNT = chunk.compile(buffer);
        out.println("VBO generated, time took: " + (System.currentTimeMillis() - startAt));

        out.println("ho compilato: " + VERT_COUNT + " vertici!");

        VBO vbo = new VBO();
        vbo.loadData((FloatBuffer) buffer.data.flip(), VBODataUsage.STATIC_DRAW);
        vbo.bind();

        new VertexLinker(DataType.FLOAT)
                .attrib(0, 3)
                .attrib(1, 4)
                .attrib(2, 2)
                .setup();

        FloatBuffer matBuf = BufferUtils.createFloatBuffer(16);

        int fpsCounter = 0;
        long lastTime = 0;

        while (!win.isClosed()) {
            if (System.currentTimeMillis() - lastTime >= 1000) {
                out.println("FPS: " + fpsCounter);
                fpsCounter = 0;
                lastTime = System.currentTimeMillis();
            }
            fpsCounter++;

            // todo replace gl11 functions glClear glClearColor
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(0f, 0f, 0f, 0f);

            updateCamera(win, camera, 0.05f);

            uniformer.setUniformMatrix4("camera", camera.getMatrix().get(matBuf));
            Drawer.drawArrays(DrawMode.QUADS, 0, VERT_COUNT);

            // canRender checks
            if (win.getKey(Key.K) || win.getKey(Key.ESCAPE)) {
                break;
            } else if (win.getKey(Key.E)) {
                throw new IllegalStateException("canRender does not gone well");
            }

            win.update();
        }

        program.destroy();
        win.destroy();
    }

    private static void updateCamera(Window win, Camera camera, float sensitivity) {
        Vector3f position = camera.getPosition();
        // checks inputs
        int state;
        if (win.getKey(Key.W))
            camera.setPosition(position.add(camera.getForward().mul(sensitivity)));
        if (win.getKey(Key.S))
            camera.setPosition(position.add(camera.getForward().mul(-sensitivity)));
        if (win.getKey(Key.A))
            camera.setPosition(position.add(camera.getRight().mul(-sensitivity)));
        if (win.getKey(Key.D))
            camera.setPosition(position.add(camera.getRight().mul(sensitivity)));
        if (win.getKey(Key.LEFT_SHIFT))
            camera.setPosition(position.add(camera.getUp().mul(-sensitivity)));
        if (win.getKey(Key.SPACE))
            camera.setPosition(position.add(camera.getUp().mul(sensitivity)));
    }
}
