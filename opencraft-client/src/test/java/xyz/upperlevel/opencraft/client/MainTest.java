package xyz.upperlevel.opencraft.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import xyz.upperlevel.opencraft.client.view.WorldView;
import xyz.upperlevel.opencraft.client.view.WorldViewer;
import xyz.upperlevel.opencraft.client.resource.texture.TextureBakery;
import xyz.upperlevel.opencraft.common.world.Location;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.opengl.shader.ShaderUtil;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.window.Glfw;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.GlfwCursorMoveEventHandler;
import xyz.upperlevel.ulge.window.event.key.Key;

import static java.lang.System.out;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;

public class MainTest {

    public static WorldViewer VIEWER;

    public static Window win;

    public static void main(String[] a) {
        win = Glfw.createWindowSettings()
                .setSamples(4)
                .createWindow(500, 500, "game canRender", false);

        // registers cursor addPosition event
        GlfwCursorMoveEventHandler cmHandler = Glfw.events().CURSOR_MOVE.create();
        cmHandler.register(new CursorMoveEvent() {
            private double lastX = 0, lastY = 0;

            @Override
            public void onCall(Window window, double x, double y) {
                double movX = x - lastX;
                double movY = y - lastY;

                Location l = VIEWER.getLoc();
                l.setYaw(l.getYaw() + (float) movX);
                l.setPitch(l.getPitch() + (float) movY);

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

        System.out.println("max texture size: " +
                glGetInteger(GL_MAX_TEXTURE_SIZE));
        System.out.println("max texture layers in tex array: " +
                glGetInteger(GL30.GL_MAX_ARRAY_TEXTURE_LAYERS));

        // creates a shader program and loads shader, finally bind it and keep it bound
        Program program = new Program();
        ClassLoader classLoader = MainTest.class.getClassLoader();
        program.attach(ShaderUtil.load(ShaderType.VERTEX, classLoader.getResourceAsStream("shaders/simple_shader.vs")));
        program.attach(ShaderUtil.load(ShaderType.FRAGMENT, classLoader.getResourceAsStream("shaders/simple_shader.fs")));
        program.link();
        Uniformer uniformer = program.bind();

        OpenCraftServer server = OpenCraftServer.get();
        Openverse client = Openverse.get();
        // INITIALIZES PLAYER!
        VIEWER = client.getViewer();

        TextureBakery.SIMPLE_INST.getCompiled().bind();

        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        int posAtr = uniformer.getAttribLocation("position");
        out.println("pos atr location: " + posAtr);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);

        int fpsCounter = 0;

        win.setVSync(true);

        WorldView w = client.getViewer().getWorld();
        w.demand();

        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);


        long lastTime_fps = 0;
        long lastTime_py = 0;

        while (!win.isClosed()) {
            long t;
            t = System.currentTimeMillis();
            if (t - lastTime_fps >= 1000) {
                out.println("FPS: " + fpsCounter);
                fpsCounter = 0;
                lastTime_fps = t;
            }
            fpsCounter++;

            //PhysicEngine.in.update(VIEWER, VIEWER.getWorld());


            // todo replace gl11 functions glClear glClearColor
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(155f / 255f, 193f / 255f, 1f, 1f);

            //updateCamera(win, 0.5f);

            VIEWER.draw(win, uniformer);

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
        if (win.getKey(Key.KEY_0))
            VIEWER.getLoc().set(1, 7, 1);
    }
}
