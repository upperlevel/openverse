package xyz.upperlevel.opencraft;

import org.joml.Vector3f;
import xyz.upperlevel.graphicengine.api.util.math.Camera;
import xyz.upperlevel.graphicengine.api.opengl.model.Model;
import xyz.upperlevel.graphicengine.api.opengl.model.VertexDefiner;
import xyz.upperlevel.graphicengine.api.opengl.model.Vertices;
import xyz.upperlevel.graphicengine.api.opengl.shader.Program;
import xyz.upperlevel.graphicengine.api.opengl.shader.Shader;
import xyz.upperlevel.graphicengine.api.opengl.shader.Uniformer;
import xyz.upperlevel.graphicengine.api.opengl.shader.loader.SimpleShaderSourceLoader;
import xyz.upperlevel.graphicengine.api.util.math.Rot;
import xyz.upperlevel.graphicengine.api.window.GLFW;
import xyz.upperlevel.graphicengine.api.window.Window;
import xyz.upperlevel.graphicengine.api.window.event.CursorMoveEvent;
import xyz.upperlevel.graphicengine.api.window.event.WindowEventHandler;
import xyz.upperlevel.graphicengine.api.util.Color;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import xyz.upperlevel.graphicengine.api.window.event.action.Action;
import xyz.upperlevel.graphicengine.api.window.event.action.GLFWAction;
import xyz.upperlevel.graphicengine.api.window.event.key.GLFWKey;
import xyz.upperlevel.opencraft.world.*;
import xyz.upperlevel.opencraft.world.FlatChunkGenerator;
import xyz.upperlevel.opencraft.world.block.id.BlockId;

import java.io.File;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glGetUniformi;
import static org.lwjgl.opengl.GL20.glUniform1i;

public class TestMain {

    private static final Vertices vertices = new Vertices(8)
            // Back face
            .add(-1f, -1f, -1f, 0f, 0f, -1f, 0.0f, 0.0f) // Bottom-left
            .add(1f, 1f, -1f, 0f, 0f, -1f, 1.0f, 1.0f) // top-right
            .add(1f, -1f, -1f, 0f, 0f, -1f, 1.0f, 0.0f) // bottom-right
            .add(1f, 1f, -1f, 0f, 0f, -1f, 1.0f, 1.0f) // top-right
            .add(-1f, -1f, -1f, 0f, 0f, -1f, 0.0f, 0.0f) // bottom-left
            .add(-1f, 1f, -1f, 0f, 0f, -1f, 0.0f, 1.0f) // top-left
            // Front face
            .add(-1f, -1f, 1f, 0f, 0f, 1f, 0.0f, 0.0f) // bottom-left
            .add(1f, -1f, 1f, 0f, 0f, 1f, 1.0f, 0.0f) // bottom-right
            .add(1f, 1f, 1f, 0f, 0f, 1f, 1.0f, 1.0f) // top-right
            .add(1f, 1f, 1f, 0f, 0f, 1f, 1.0f, 1.0f) // top-right
            .add(-1f, 1f, 1f, 0f, 0f, 1f, 0.0f, 1.0f) // top-left
            .add(-1f, -1f, 1f, 0f, 0f, 1f, 0.0f, 0.0f) // bottom-left
            // Left face
            .add(-1f, 1f, 1f, -1f, 0f, 0f, 1.0f, 0.0f) // top-right
            .add(-1f, 1f, -1f, -1f, 0f, 0f, 1.0f, 1.0f) // top-left
            .add(-1f, -1f, -1f, -1f, 0f, 0f, 0.0f, 1.0f) // bottom-left
            .add(-1f, -1f, -1f, -1f, 0f, 0f, 0.0f, 1.0f) // bottom-left
            .add(-1f, -1f, 1f, -1f, 0f, 0f, 0.0f, 0.0f) // bottom-right
            .add(-1f, 1f, 1f, -1f, 0f, 0f, 1.0f, 0.0f) // top-right
            // Right face
            .add(1f, 1f, 1f, 1f, 0f, 0f, 1.0f, 0.0f) // top-left
            .add(1f, -1f, -1f, 1f, 0f, 0f, 0.0f, 1.0f) // bottom-right
            .add(1f, 1f, -1f, 1f, 0f, 0f, 1.0f, 1.0f) // top-right
            .add(1f, -1f, -1f, 1f, 0f, 0f, 0.0f, 1.0f) // bottom-right
            .add(1f, 1f, 1f, 1f, 0f, 0f, 1.0f, 0.0f) // top-left
            .add(1f, -1f, 1f, 1f, 0f, 0f, 0.0f, 0.0f) // bottom-left
            // Bottom face
            .add(-1f, -1f, -1f, 0f, -1f, 0f, 0.0f, 1.0f) // top-right
            .add(1f, -1f, -1f, 0f, -1f, 0f, 1.0f, 1.0f)// top-left
            .add(1f, -1f, 1f, 0f, -1f, 0f, 1.0f, 0.0f) // bottom-left
            .add(1f, -1f, 1f, 0f, -1f, 0f, 1.0f, 0.0f) // bottom-left
            .add(-1f, -1f, 1f, 0f, -1f, 0f, 0.0f, 0.0f) // bottom-right
            .add(-1f, -1f, -1f, 0f, -1f, 0f, 0.0f, 1.0f) // top-right
            // Top face
            .add(-1f, 1f, -1f, 0f, 1f, 0f, 0.0f, 1.0f) // top-left
            .add(1f, 1f, 1f, 0f, 1f, 0f, 1.0f, 0.0f) // bottom-right
            .add(1f, 1f, -1f, 0f, 1f, 0f, 1.0f, 1.0f) // top-right
            .add(1f, 1f, 1f, 0f, 1f, 0f, 1.0f, 0.0f) // bottom-right
            .add(-1f, 1f, -1f, 0f, 1f, 0f, 0.0f, 1.0f) // top-left
            .add(-1f, 1f, 1f, 0f, 1f, 0f, 0.0f, 0.0f); // bottom-left

    private static final Vertices old = new Vertices(8)
            .add(-1f, -1f, -1f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f)
            .add(1f, -1f, -1f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f)
            .add(1f, 1f, -1f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f)
            .add(1f, 1f, -1f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f)
            .add(-1f, 1f, -1f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f)
            .add(-1f, -1f, -1f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f)

            .add(-1f, -1f, 1f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f)
            .add(1f, -1f, 1f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f)
            .add(1f, 1f, 1f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
            .add(1f, 1f, 1f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
            .add(-1f, 1f, 1f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f)
            .add(-1f, -1f, 1f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f)

            .add(-1f, 1f, 1f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f)
            .add(-1f, 1f, -1f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f)
            .add(-1f, -1f, -1f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f)
            .add(-1f, -1f, -1f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f)
            .add(-1f, -1f, 1f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f)
            .add(-1f, 1f, 1f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f)

            .add(1f, 1f, 1f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f)
            .add(1f, 1f, -1f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f)
            .add(1f, -1f, -1f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f)
            .add(1f, -1f, -1f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f)
            .add(1f, -1f, 1f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f)
            .add(1f, 1f, 1f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f)

            .add(-1f, -1f, -1f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f)
            .add(1f, -1f, -1f, 0.0f, -1.0f, 0.0f, 1.0f, 1.0f)
            .add(1f, -1f, 1f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f)
            .add(1f, -1f, 1f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f)
            .add(-1f, -1f, 1f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f)
            .add(-1f, -1f, -1f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f)

            .add(-1f, 1f, -1f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f)
            .add(1f, 1f, -1f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f)
            .add(1f, 1f, 1f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f)
            .add(1f, 1f, 1f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f)
            .add(-1f, 1f, 1f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f)
            .add(-1f, 1f, -1f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f);

    public static final Camera camera = new Camera();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Window window = GLFW.createWindow(750, 500, "Io ml skp");
        glfwWindowHint(GLFW_SAMPLES, 4);
        WindowEventHandler eventHandler;
        eventHandler = GLFW.events().CURSOR_MOVE.inst();
        eventHandler.register(new CursorMoveEvent() {
            private double lastX = 0, lastY = 0;

            public void onCall(Window window, double x, double y) {
                double movX = lastX - x;
                double movY = y - lastY;

                Rot rot = camera.getRotation();
                rot.add(new Rot((float) Math.toRadians(-movX), Math.toRadians((float) movY), 0f));

                lastX = x;
                lastY = y;
            }
        });
        window.disableCursor();
        window.setCursorPosition(0f, 0f);
        window.registerEventHandler(eventHandler);
        window.contextualize();
        window.show();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA);

        Shader.CompileStatus compilation;

        Shader shader;
        shader = new Shader(Shader.Type.VERTEX);
        compilation = shader.compileSource(new SimpleShaderSourceLoader().load(new File("C:/Users/Lorenzo/Desktop/shaders/textureBasicVertexShader.glsl")));

        System.out.println("VERTEX COMPILATION: " + compilation.getLog());

        Program program = new Program();
        program.attach(shader);

        shader = new Shader(Shader.Type.FRAGMENT);
        compilation = shader.compileSource(new SimpleShaderSourceLoader().load(new File("C:/Users/Lorenzo/Desktop/shaders/textureBasicFragmentShader.glsl")));
        program.attach(shader);

        System.out.println("FRAGMENT COMPILATION: " + compilation.getLog());

        program.link();
        Uniformer uniformer = program.bind();

        Model model = new Model((mode, model1) -> GL11.glDrawArrays(mode.getId(), 0, model1.getVerticesCount()));
        model.loadData(vertices);
        System.out.println("VERTICES COUNT: " + model.getVerticesCount());
        model.setDefiner(VertexDefiner.builder()
                .attrib(uniformer.getAttribLocation("position"), 3, 0)
                .attrib(uniformer.getAttribLocation("normal"), 3, 3)
                .attrib(uniformer.getAttribLocation("texCoords"), 2, 6)
                .build()
        );
        model.bind();

        uniformer.setUni("pointLightsCount", 1);
        uniformer.setUni("pointLights[0].position", 1f, 1f, 0f);
        uniformer.setUni("pointLights[0].ambient", 0.2f, 0.2f, 0.2f);
        uniformer.setUni("pointLights[0].diffuse", 1f, 1f, 1f);
        uniformer.setUni("pointLights[0].specular", 1f, 1f, 1f);
        uniformer.setUni("pointLights[0].constant", 1f);
        uniformer.setUni("pointLights[0].linear", 0.09f);
        uniformer.setUni("pointLights[0].quadratic", 0.032f);

        uniformer.setUni("directionLight.direction", 0.2f, 1f, 0f);
        uniformer.setUni("directionLight.ambient", 0.2f, 0.2f, 0.2f);
        uniformer.setUni("directionLight.diffuse", 0.35f, 0.35f, 0.35f);
        uniformer.setUni("directionLight.specular", 1f, 1f, 1f);

        World world = new World(new FlatChunkGenerator());

        glUniform1i(uniformer.getUniLocation("material.specular"), 1);
        glUniform1i(uniformer.getUniLocation("material.diffuse"), 0);

        window.setVSync(true);

        System.out.println("DIFFUSE TEXTURE: " + glGetUniformi(program.getId(), uniformer.getUniLocation("material.diffuse")));
        System.out.println("SPECULAR TEXTURE: " + glGetUniformi(program.getId(), uniformer.getUniLocation("material.specular")));

        glFrontFace(GL_CW);
        glCullFace(GL_FRONT);
        glEnable(GL_CULL_FACE);

        long last = 0;
        int fps = 0;

        while (!window.isClosed()) {

            fps++;
            if (System.currentTimeMillis() - last >= 1000) {
                System.out.println("fps: " + fps);
                fps = 0;
                last = System.currentTimeMillis();
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(0f, 0f, 0f, 0f);

            processInput(window);

            FloatBuffer buffer;

            Vector3f position = camera.getPosition();
            Chunk chunk = world.getChunk(world.getChunkX(position.x), world.getChunkY(position.y), world.getChunkZ(position.z));
            chunk.load();

            System.out.println("size: " + chunk.cache.getRenderer().size());
            Block test = chunk.getBlock(0, 2, 0);

            buffer = BufferUtils.createFloatBuffer(16);
            uniformer.setUniMatrix("camera", camera.getMatrix().get(buffer));
            uniformer.setUni("viewPosition", camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);

            /*
            BlockId cache = Blocks.WOOD;

            glActiveTexture(GL_TEXTURE0);
            cache.getMaterial().getTexture().bind();

            glActiveTexture(GL_TEXTURE1);
            cache.getMaterial().getSpecularTexture().bind();

            uniformer.setUniform("material.shininess", 32f);

            Color color = cache.getColor();
            uniformer.setUniform("objectColor", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

            Matrix4f translation = new Matrix4f();
            buffer = BufferUtils.createFloatBuffer(16);
            uniformer.setMatrixUniform("model", translation.get(buffer));

            model.draw();*/

            chunk.cache.getRenderer().forEach(block -> {
                BlockId data = block.getData();
                if (data.equals(BlockId.EMPTY))
                    return;

                glActiveTexture(GL_TEXTURE0);
                data.getMaterial().getTexture().bind();

                glActiveTexture(GL_TEXTURE1);
                data.getMaterial().getSpecularTexture().bind();

                uniformer.setUni("material.shininess", 256f);

                Color color = data.getColor();
                uniformer.setUni("objectColor",
                        color.getRedF(),
                        color.getGreenF(),
                        color.getBlueF(),
                        color.getAlphaF());

                Matrix4f translation = new Matrix4f();

                float w = 1f / Chunk.WIDTH;
                float h = 1f / 16;
                float l = 1f / Chunk.LENGTH;

                float movX = (float) (block.getLoc().asChunkX() * 2f / Chunk.WIDTH - 1f + w);
                float movY = (float) (block.getLoc().asChunkY() * 2f / 16 - 1f + h);
                float movZ = (float) (block.getLoc().asChunkZ() * 2f / Chunk.LENGTH - 1f + l);

                translation.translate(movX, movY, movZ);
                translation.scale(w, h, l);

                FloatBuffer a = BufferUtils.createFloatBuffer(16);
                uniformer.setUniMatrix("model", translation.get(a));

                model.draw();
            });


/* LOL
            for (int x = 0; x < Chunk.WIDTH; x++) {
                for (int y = 0; y < Chunk.HEIGHT; y++) {
                    for (int z = 0; z < Chunk.LENGTH; z++) {
                        Block block = chunk.getBlock(x, y, z);
                        BlockId data = block.getData();
                        if (data != BlockId.EMPTY) {
                            glActiveTexture(GL_TEXTURE0);
                            data.getMaterial().getTexture().bind();

                            glActiveTexture(GL_TEXTURE1);
                            data.getMaterial().getSpecularTexture().bind();

                            uniformer.setUni("material.shininess", 256f);

                            Color color = data.getColor();
                            uniformer.setUni("objectColor",
                                    color.getRedF(),
                                    color.getGreenF(),
                                    color.getBlueF(),
                                    color.getAlphaF());

                            Matrix4f translation = new Matrix4f();

                            float w = 1f / Chunk.WIDTH;
                            float h = 1f / 16;
                            float l = 1f / Chunk.LENGTH;

                            float movX = x * 2f / Chunk.WIDTH - 1f + w;
                            float movY = y * 2f / 16 - 1f + h;
                            float movZ = z * 2f / Chunk.LENGTH - 1f + l;

                            translation.translate(movX, movY, movZ);
                            translation.scale(w, h, l);

                            buffer = BufferUtils.createFloatBuffer(16);
                            uniformer.setUniMatrix("model", translation.get(buffer));

                            model.draw();
                        }
                    }
                }
            }
*/
            window.update();
        }

        model.unbind();
        model.destroy();
        GLFW.destroy();
    }

    public static final float SENSITIVITY = 0.1f;

    public static boolean processInput(Window window) {
        Action state;
        state = window.getKeyState(GLFWKey.W);
        if (state.equals(GLFWAction.PRESS) || state.equals(GLFWAction.REPEAT))
            camera.getPosition().add(camera.getForward().mul(SENSITIVITY));
        state = window.getKeyState(GLFWKey.S);
        if (state.equals(GLFWAction.PRESS) || state.equals(GLFWAction.REPEAT))
            camera.getPosition().add(camera.getForward().mul(-SENSITIVITY));
        state = window.getKeyState(GLFWKey.A);
        if (state.equals(GLFWAction.PRESS) || state.equals(GLFWAction.REPEAT))
            camera.getPosition().add(camera.getRight().mul(-SENSITIVITY));
        state = window.getKeyState(GLFWKey.D);
        if (state.equals(GLFWAction.PRESS) || state.equals(GLFWAction.REPEAT))
            camera.getPosition().add(camera.getRight().mul(SENSITIVITY));
        state = window.getKeyState(GLFWKey.LEFT_SHIFT);
        if (state.equals(GLFWAction.PRESS) || state.equals(GLFWAction.REPEAT))
            camera.getPosition().add(camera.getUp().mul(-SENSITIVITY));
        state = window.getKeyState(GLFWKey.SPACE);
        if (state.equals(GLFWAction.PRESS) || state.equals(GLFWAction.REPEAT))
            camera.getPosition().add(camera.getUp().mul(SENSITIVITY));
        state = window.getKeyState(GLFWKey.ESCAPE);
        if (state.equals(GLFWAction.PRESS)) {
            window.close();
            return false;
        }
        return true;
    }
}
