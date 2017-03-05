package xyz.upperlevel.opencraft.client;

import org.lwjgl.opengl.GL11;
import xyz.upperlevel.opencraft.client.render.WorldViewer;
import xyz.upperlevel.opencraft.server.OpenCraftServer;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.opengl.shader.ShaderUtil;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.opengl.texture.Texture2D;
import xyz.upperlevel.ulge.opengl.texture.TextureFormat;
import xyz.upperlevel.ulge.opengl.texture.TextureParameters;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.window.Glfw;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.GlfwCursorMoveEventHandler;
import xyz.upperlevel.ulge.window.event.key.Key;

import static java.lang.System.out;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class MainTest {

    public static WorldViewer VIEWER;

    public static void main(String[] a) {
        Window win = Glfw.createWindowSettings()
                .setSamples(4)
                .createWindow(500, 500, "game canRender", false);

        // registers cursor move event
        GlfwCursorMoveEventHandler cmHandler = Glfw.events().CURSOR_MOVE.create();
        cmHandler.register(new CursorMoveEvent() {
            private double lastX = 0, lastY = 0;

            @Override
            public void onCall(Window window, double x, double y) {
                double movX = x - lastX;
                double movY = y - lastY;

                VIEWER.rotate((float) movX, (float) movY);
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
        ClassLoader classLoader = MainTest.class.getClassLoader();
        program.attach(ShaderUtil.load(ShaderType.VERTEX, classLoader.getResourceAsStream("shaders/simple_shader.vs")));
        program.attach(ShaderUtil.load(ShaderType.FRAGMENT, classLoader.getResourceAsStream("shaders/simple_shader.fs")));
        program.link();
        Uniformer uniformer = program.bind();

        OpenCraftServer server = OpenCraftServer.get();
        OpenCraftClient client = OpenCraftClient.get();
        // INITIALIZES PLAYER!
        VIEWER = client.getViewer();

        new Texture2D()
                .loadImage(TextureFormat.RGBA, new ImageContent(OpenCraft.get().getAssetManager().getTextureManager().getMerger()))
                .bind();
        TextureParameters.getDefault().setup();

        int posAtr = uniformer.getAttribLocation("position");
        out.println("pos atr location: " + posAtr);

        glEnable(GL_MULTISAMPLE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);

        int fpsCounter = 0;
        long lastTime = 0;

        win.setVSync(true);

        while (!win.isClosed()) {
            if (System.currentTimeMillis() - lastTime >= 1000) {
                out.println("FPS: " + fpsCounter);
                fpsCounter = 0;
                lastTime = System.currentTimeMillis();
            }
            fpsCounter++;

            // todo replace gl11 functions glClear glClearColor
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(155f / 255f, 193f / 255f, 1f, 1f);

            updateCamera(win, 0.5f);

            VIEWER.draw(uniformer);

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

    private static void updateCamera(Window win, float sensitivity) {
        if (win.getKey(Key.W))
            VIEWER.forward(sensitivity);
        if (win.getKey(Key.S))
            VIEWER.backward(sensitivity);
        if (win.getKey(Key.A))
            VIEWER.left(sensitivity);
        if (win.getKey(Key.D))
            VIEWER.right(sensitivity);
        if (win.getKey(Key.LEFT_SHIFT))
            VIEWER.down(sensitivity);
        if (win.getKey(Key.SPACE))
            VIEWER.up(sensitivity);

        if (win.getKey(Key.KP_1)) {
            VIEWER.setPosition(0f, 16f, 0f);
        }
        if (win.getKey(Key.KP_7)) {
            VIEWER.setPosition(0f, 16f, 16f);
        }
        if (win.getKey(Key.KP_9)) {
            VIEWER.setPosition(16f, 16f, 16f);
        }
        if (win.getKey(Key.KP_3)) {
            VIEWER.setPosition(16f, 16f, 0f);
        }
    }
}
