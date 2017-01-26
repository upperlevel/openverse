package xyz.upperlevel.opencraft;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.opencraft.world.block.*;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.opengl.shader.ShaderUtil;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.Colors;
import xyz.upperlevel.ulge.util.math.Camera;
import xyz.upperlevel.ulge.util.math.Rot;
import xyz.upperlevel.ulge.window.GLFW;
import xyz.upperlevel.ulge.window.Window;
import xyz.upperlevel.ulge.window.event.CursorMoveEvent;
import xyz.upperlevel.ulge.window.event.GLFWCursorMoveEventHandler;
import xyz.upperlevel.ulge.window.event.key.Key;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.ChunkGenerators;
import xyz.upperlevel.opencraft.world.World;

import java.nio.FloatBuffer;

import static java.lang.System.out;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameTest {

    public static void main(String[] a) {
        Camera camera = new Camera();
        camera.setPosition(new Vector3f());
        camera.setRotation(new Rot());
        camera.setNearAndFarPlane(0.01f, 100f);

        float[] faceVert = new float[]{
                -1f, -1f, 0f,//0
                1f, -1f, 0f,//1
                1f, 1f, 0f,//2

                1f, 1f, 0f,//2
                -1f, 1f, 0f,//3
                -1f, -1f, 0f,//0
        };

        Window win = GLFW.createWindow(500, 500, "game test", false);
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
        ClassLoader classLoader = GameTest.class.getClassLoader();
        program.attach(ShaderUtil.load(ShaderType.VERTEX, classLoader.getResourceAsStream("resources/vertex.glsl")));
        program.attach(ShaderUtil.load(ShaderType.FRAGMENT, classLoader.getResourceAsStream("resources/fragment.glsl")));
        program.link();
        Uniformer uniformer = program.bind();

        out.println("program color uniform existing: " + uniformer.hasUniform("color"));

        // bind and keep it bound for the rest of the program

        VBO vbo = new VBO();
        vbo.loadData(faceVert, VBOUsage.STATIC_DRAW);
        vbo.bind();

        int posAtr = uniformer.getAttribLocation("position");
        out.println("pos atr location: " + posAtr);

        new VertexLinker(DataType.FLOAT)
                .attrib(0, 3)
                .setup();

        // attempts to link vertices

        // world implementation
        World world = new World(ChunkGenerators.FLAT);
        Chunk chunk = world.getChunk(0, 0, 0); // gets a random-coords chunk
        chunk.load();

        BlockState blockState = chunk.getBlockState(0, 0, 0);
        out.println("block id at {0,0,0} of chunk {0,0,0}: " + blockState.getId());
        out.println("block state shape components: " + blockState.getShape().getComponents().size());

        // COLLISION TEST
        BlockComponent collisionComp = new BlockComponent("collision_comp");
        collisionComp.setZone(new BlockComponentZone(
                new Vector3f(.5f, 0, 0),
                new Vector3f(1.1f)
        ));
        collisionComp.getFaceBuffer().computeFaces(blockState.getShape().getComponent("main_block").get());
        out.println("attempting to draw " + collisionComp.getFaceBuffer().getFaces().size() + " faces for collision component");

        blockState.getFaceBuffer().computeFaces(collisionComp); // computes faces once
        out.println("attempting to draw " + blockState.getFaceBuffer().getFaces().size() + " faces for test component");

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);

        FloatBuffer matBuf = BufferUtils.createFloatBuffer(16);

        float scale = 0f;

        while (!win.isClosed()) {
            // todo replace gl11 functions glClear glClearColor
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glClearColor(0f, 0f, 0f, 0f);

            updateCamera(win, camera, 0.05f);
            uniformer.setUniformMatrix("camera", camera.getMatrix().get(matBuf));

            //<editor-fold desc="manual face drawing">
            /*
            // REFERRING POINT
            {
                uniformer.setUniformMatrix("model", new Matrix4f().translate(0f, 0f, 1f).get(matBuf));
                uniformer.setUniform("color", Colors.WHITE);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }
            // REFERRING POINT
            {
                uniformer.setUniformMatrix("model", new Matrix4f().translate(0f, 0f, -1f).get(matBuf));
                uniformer.setUniform("color", Colors.WHITE);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }

            BlockComponentZone zone = new BlockComponentZone(
                    new Vector3f(1f),
                    new Vector3f(.25f)
            );
            BlockComponent testComponent = new BlockComponent("test_bc", zone);
            // FORWARD FACE
            {
                BlockFace face = testComponent.getFace(BlockFacePosition.FORWARD);
                uniformer.setUniformMatrix("model", BlockFacePosition.FORWARD.rotateToCubeRotation(testHereMatrix(face)).get(matBuf));
                uniformer.setUniform("color", Colors.GREEN);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }
            // BACKWARD FACE
            {
                BlockFace face = testComponent.getFace(BlockFacePosition.BACKWARD);
                uniformer.setUniformMatrix("model", BlockFacePosition.BACKWARD.rotateToCubeRotation(testHereMatrix(face)).get(matBuf));
                uniformer.setUniform("color", Colors.DARK_AQUA);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }
            // UP FACE
            {
                BlockFace face = testComponent.getFace(BlockFacePosition.UP);
                uniformer.setUniformMatrix("model", BlockFacePosition.UP.rotateToCubeRotation(testHereMatrix(face)).get(matBuf));
                uniformer.setUniform("color", Colors.RED);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }
            // DOWN FACE
            {
                BlockFace face = testComponent.getFace(BlockFacePosition.DOWN);
                uniformer.setUniformMatrix("model", BlockFacePosition.DOWN.rotateToCubeRotation(testHereMatrix(face)).get(matBuf));
                uniformer.setUniform("color", Colors.YELLOW);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }
            // LEFT FACE
            {
                BlockFace face = testComponent.getFace(BlockFacePosition.LEFT);
                uniformer.setUniformMatrix("model", BlockFacePosition.LEFT.rotateToCubeRotation(testHereMatrix(face)).get(matBuf));
                uniformer.setUniform("color", Colors.BLUE);

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            }*/
            //</editor-fold>

            uniformer.setUniform("color", Colors.RED);
            blockState.getFaceBuffer().getFaces().forEach(f -> {
                uniformer.setUniformMatrix("model", f.getPosition().rotateToCubeRotation(testHereMatrix(f)).get(matBuf));

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            });

            uniformer.setUniform("color", Colors.BLUE);
            collisionComp.getFaceBuffer().getFaces().forEach(f -> {
                uniformer.setUniformMatrix("model", f.getPosition().rotateToCubeRotation(testHereMatrix(f)).get(matBuf));

                Drawer.drawArrays(DrawMode.TRIANGLES, 0, faceVert.length / 3);
            });

            // test checks
            if (win.getKey(Key.K) || win.getKey(Key.ESCAPE)) {
                break;
            } else if (win.getKey(Key.E)) {
                throw new IllegalStateException("test does not gone well");
            }

            win.update();
        }
    }

    public static String toString(Vector3f v) {
        return "{" + v.x + "," + v.y + "," + v.z + "}";
    }

    private static Matrix4f testHereMatrix(BlockFace face) {
        BlockComponentZone zone = face.getComponent().getZone();

        Matrix4f mat = new Matrix4f();

        /*
        float x = zone.getMinX() - 1f + zone.getWidth();
        float y = zone.getMinY() - 1f + zone.getHeight();
        float z = zone.getMinZ() + 1f + zone.getMinZ();

        out.println(zone.getMinZ());

        mat.translate(x, y, z);
        mat.scale(zone.getScale());
        */

        /*
        Not completely working
        // translates faces in base of the zone they  are
        mat.translate(new Vector3f(-1f)sd
                .add(zone.getSize())
                .add(zone.getMinPosition())
                .mul(1f, 1f, -1f)
        );
        */
        mat.translate(new Vector3f(-1f)
                .add(zone.getSize())
                .add(zone.getMinPosition().mul(2))
                .mul(1f, 1f, -1f)
        );
        // translates faces in order to make a cube
        mat.translate(face.getPosition().getMod().mul(zone.getSize()));
        mat.scale(zone.getSize());
        return mat;
    }

    private static void updateCamera(Window win, Camera camera, float sensitivity) {
        Vector3f position = camera.getPosition();
        // checks inputs
        int state;
        state = glfwGetKey(win.getId(), GLFW_KEY_W);
        if (state > 0)
            camera.setPosition(position.add(camera.getForward().mul(sensitivity)));
        state = glfwGetKey(win.getId(), GLFW_KEY_S);
        if (state > 0)
            camera.setPosition(position.add(camera.getForward().mul(-sensitivity)));
        state = glfwGetKey(win.getId(), GLFW_KEY_A);
        if (state > 0)
            camera.setPosition(position.add(camera.getRight().mul(-sensitivity)));
        state = glfwGetKey(win.getId(), GLFW_KEY_D);
        if (state > 0)
            camera.setPosition(position.add(camera.getRight().mul(sensitivity)));
        state = glfwGetKey(win.getId(), GLFW_KEY_LEFT_SHIFT);
        if (state > 0)
            camera.setPosition(position.add(camera.getUp().mul(-sensitivity)));
        state = glfwGetKey(win.getId(), GLFW_KEY_SPACE);
        if (state > 0)
            camera.setPosition(position.add(camera.getUp().mul(sensitivity)));
        //out.println("camera position (" + position.x + "," + position.y + "," + position.z + ")");
    }
}