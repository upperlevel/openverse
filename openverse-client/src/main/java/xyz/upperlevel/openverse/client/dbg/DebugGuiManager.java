package xyz.upperlevel.openverse.client.dbg;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.nuklear.*;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_8_8_8_8_REV;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.glBlendEquation;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

// https://www.thecodingfox.com/nuklear-usage-guide-lwjgl

public class DebugGuiManager {
    public static final File FONT_PATH = new File("client/resources/fonts/Roboto-Regular.ttf");

    private final long mWindow;

    private final NkContext mContext;
    private final NkUserFont mDefaultFont;
    private final NkBuffer mCommandBuffer = NkBuffer.create();
    private final NkDrawNullTexture mNullTexture = NkDrawNullTexture.create();

    private int mVbo, mVao, mEbo;
    private int mProgram;
    private int mVertexShader;
    private int mFragmentShader;
    private int mTextureUniform;
    private int mProjectionMatrixUniform;

    private static final int BUFFER_INITIAL_SIZE = 4 * 1024;
    private static final int MAX_VERTEX_BUFFER   = 512 * 1024;
    private static final int MAX_ELEMENT_BUFFER  = 128 * 1024;

    private static final NkAllocator ALLOCATOR;
    private static final NkDrawVertexLayoutElement.Buffer VERTEX_LAYOUT;

    static {
        ALLOCATOR = NkAllocator.create()
                .alloc((handle, old, size) -> nmemAlloc(size))
                .mfree((handle, ptr) -> nmemFree(ptr));

        VERTEX_LAYOUT = NkDrawVertexLayoutElement.create(4)
                .position(0).attribute(NK_VERTEX_POSITION).format(NK_FORMAT_FLOAT).offset(0)
                .position(1).attribute(NK_VERTEX_TEXCOORD).format(NK_FORMAT_FLOAT).offset(8)
                .position(2).attribute(NK_VERTEX_COLOR).format(NK_FORMAT_R8G8B8A8).offset(16)
                .position(3).attribute(NK_VERTEX_ATTRIBUTE_COUNT).format(NK_FORMAT_COUNT).offset(0)
                .flip();
    }


    public DebugGuiManager(long window) {
        this.mWindow = window;
        this.mContext = NkContext.create();
        this.mDefaultFont = NkUserFont.create();

        nk_init(this.mContext, ALLOCATOR, null);

        setupWindowBindings();
        setupGraphicsObjects();
        loadFont();
    }

    private void setupWindowBindings() {
        glfwSetScrollCallback(this.mWindow, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                try (MemoryStack stack = stackPush()) {
                    NkVec2 scroll = NkVec2.mallocStack(stack)
                            .x((float) xoffset)
                            .y((float) yoffset);
                    nk_input_scroll(mContext, scroll);
                }
            }
        });

        glfwSetCharCallback(this.mWindow, new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
                nk_input_unicode(mContext, codepoint);
            }
        });

        glfwSetKeyCallback(this.mWindow, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                boolean press = action == GLFW_PRESS;
                switch (key) {
                    case GLFW_KEY_ESCAPE:
                        glfwSetWindowShouldClose(window, true);
                        break;
                    case GLFW_KEY_DELETE:
                        nk_input_key(mContext, NK_KEY_DEL, press);
                        break;
                    case GLFW_KEY_ENTER:
                        nk_input_key(mContext, NK_KEY_ENTER, press);
                        break;
                    case GLFW_KEY_TAB:
                        nk_input_key(mContext, NK_KEY_TAB, press);
                        break;
                    case GLFW_KEY_BACKSPACE:
                        nk_input_key(mContext, NK_KEY_BACKSPACE, press);
                        break;
                    case GLFW_KEY_UP:
                        nk_input_key(mContext, NK_KEY_UP, press);
                        break;
                    case GLFW_KEY_DOWN:
                        nk_input_key(mContext, NK_KEY_DOWN, press);
                        break;
                    case GLFW_KEY_HOME:
                        nk_input_key(mContext, NK_KEY_TEXT_START, press);
                        nk_input_key(mContext, NK_KEY_SCROLL_START, press);
                        break;
                    case GLFW_KEY_END:
                        nk_input_key(mContext, NK_KEY_TEXT_END, press);
                        nk_input_key(mContext, NK_KEY_SCROLL_END, press);
                        break;
                    case GLFW_KEY_PAGE_DOWN:
                        nk_input_key(mContext, NK_KEY_SCROLL_DOWN, press);
                        break;
                    case GLFW_KEY_PAGE_UP:
                        nk_input_key(mContext, NK_KEY_SCROLL_UP, press);
                        break;
                    case GLFW_KEY_LEFT_SHIFT:
                    case GLFW_KEY_RIGHT_SHIFT:
                        nk_input_key(mContext, NK_KEY_SHIFT, press);
                        break;
                    case GLFW_KEY_LEFT_CONTROL:
                    case GLFW_KEY_RIGHT_CONTROL:
                        if (press) {
                            nk_input_key(mContext, NK_KEY_COPY, glfwGetKey(window, GLFW_KEY_C) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_PASTE, glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_CUT, glfwGetKey(window, GLFW_KEY_X) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_TEXT_UNDO, glfwGetKey(window, GLFW_KEY_Z) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_TEXT_REDO, glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_TEXT_WORD_LEFT, glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_TEXT_WORD_RIGHT, glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_TEXT_LINE_START, glfwGetKey(window, GLFW_KEY_B) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_TEXT_LINE_END, glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS);
                        } else {
                            nk_input_key(mContext, NK_KEY_LEFT, glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_RIGHT, glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
                            nk_input_key(mContext, NK_KEY_COPY, false);
                            nk_input_key(mContext, NK_KEY_PASTE, false);
                            nk_input_key(mContext, NK_KEY_CUT, false);
                            nk_input_key(mContext, NK_KEY_SHIFT, false);
                        }
                        break;
                }
            }
        });

        glfwSetCursorPosCallback(this.mWindow, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                nk_input_motion(mContext, (int) xpos, (int) ypos);
            }
        });

        glfwSetMouseButtonCallback(this.mWindow, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                try (MemoryStack stack = stackPush()) {
                    DoubleBuffer cx = stack.mallocDouble(1);
                    DoubleBuffer cy = stack.mallocDouble(1);

                    glfwGetCursorPos(window, cx, cy);

                    int x = (int)cx.get(0);
                    int y = (int)cy.get(0);

                    int nkButton;
                    switch (button) {
                        case GLFW_MOUSE_BUTTON_RIGHT:
                            nkButton = NK_BUTTON_RIGHT;
                            break;
                        case GLFW_MOUSE_BUTTON_MIDDLE:
                            nkButton = NK_BUTTON_MIDDLE;
                            break;
                        default:
                            nkButton = NK_BUTTON_LEFT;
                    }
                    nk_input_button(mContext, nkButton, x, y, action == GLFW_PRESS);
                }
            }
        });
    }

    private void setupGraphicsObjects() {
        String NK_SHADER_VERSION = Platform.get() == Platform.MACOSX ? "#version 150\n" : "#version 300 es\n";
        String vertexShaderSrc =
                NK_SHADER_VERSION +
                        "uniform mat4 ProjMtx;\n" +
                        "in vec2 Position;\n" +
                        "in vec2 TexCoord;\n" +
                        "in vec4 Color;\n" +
                        "out vec2 Frag_UV;\n" +
                        "out vec4 Frag_Color;\n" +
                        "void main() {\n" +
                        "   Frag_UV = TexCoord;\n" +
                        "   Frag_Color = Color;\n" +
                        "   gl_Position = ProjMtx * vec4(Position.xy, 0, 1);\n" +
                        "}\n";

        String fragmentShaderSrc =
                NK_SHADER_VERSION +
                        "precision mediump float;\n" +
                        "uniform sampler2D Texture;\n" +
                        "in vec2 Frag_UV;\n" +
                        "in vec4 Frag_Color;\n" +
                        "out vec4 Out_Color;\n" +
                        "void main(){\n" +
                        "   Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n" +
                        "}\n";

        nk_buffer_init(mCommandBuffer, ALLOCATOR, BUFFER_INITIAL_SIZE);

        mProgram = glCreateProgram();

        mVertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(mVertexShader, vertexShaderSrc);
        glCompileShader(mVertexShader);

        if (glGetShaderi(mVertexShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException(
                    String.format("Vertex shader compilation failed: %s", glGetShaderInfoLog(mVertexShader))
            );
        }

        glAttachShader(mProgram, mVertexShader);

        mFragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(mFragmentShader, fragmentShaderSrc);
        glCompileShader(mFragmentShader);

        if (glGetShaderi(mFragmentShader, GL_COMPILE_STATUS) != GL_TRUE) {
            throw new IllegalStateException(
                    String.format("Fragment shader compilation failed: %s", glGetShaderInfoLog(mFragmentShader))
            );
        }

        glAttachShader(mProgram, mFragmentShader);

        glLinkProgram(mProgram);

        if (glGetProgrami(mProgram, GL_LINK_STATUS) != GL_TRUE) {
            throw new IllegalStateException(
                    String.format("Fragment shader compilation failed: %s", glGetProgramInfoLog(mProgram))
            );
        }

        mTextureUniform          = glGetUniformLocation(mProgram, "Texture");
        mProjectionMatrixUniform = glGetUniformLocation(mProgram, "ProjMtx");

        int posAttrib = glGetAttribLocation(mProgram, "Position");
        int uvAttrib  = glGetAttribLocation(mProgram, "TexCoord");
        int colAttrib = glGetAttribLocation(mProgram, "Color");

        { // Buffers init
            mVbo = glGenBuffers();
            mEbo = glGenBuffers();
            mVao = glGenVertexArrays();

            glBindVertexArray(mVao);
            glBindBuffer(GL_ARRAY_BUFFER, mVbo);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mEbo);

            glEnableVertexAttribArray(posAttrib);
            glEnableVertexAttribArray(uvAttrib);
            glEnableVertexAttribArray(colAttrib);

            glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false, 20, 0);
            glVertexAttribPointer(uvAttrib, 2, GL_FLOAT, false, 20, 8);
            glVertexAttribPointer(colAttrib, 4, GL_UNSIGNED_BYTE, true, 20, 16);
        }

        { // Null texture init
            int nullTexId = glGenTextures();

            mNullTexture.texture().id(nullTexId);
            mNullTexture.uv().set(0.5f, 0.5f);

            glBindTexture(GL_TEXTURE_2D, nullTexId);
            try (MemoryStack stack = stackPush()) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 1, 1, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8_REV, stack.ints(0xFFFFFFFF));
            }
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        }

        glBindTexture(GL_TEXTURE_2D, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    // todo in util?
    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    // todo in util?
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = DebugGuiManager.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return buffer.slice();
    }

    private void loadFont() {
        int BITMAP_W = 1024;
        int BITMAP_H = 1024;

        int FONT_HEIGHT = 18;
        int fontTexID   = glGenTextures();

        STBTTFontinfo fontInfo = STBTTFontinfo.create();
        STBTTPackedchar.Buffer cdata = STBTTPackedchar.create(95);

        float scale;
        float descent;

        ByteBuffer ttf;
        try {
            ttf = ioResourceToByteBuffer(FONT_PATH.toPath().toString(), 512 * 1024);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load font file", e);
        }

        try (MemoryStack stack = stackPush()) {
            stbtt_InitFont(fontInfo, ttf);
            scale = stbtt_ScaleForPixelHeight(fontInfo, FONT_HEIGHT);

            IntBuffer d = stack.mallocInt(1);
            stbtt_GetFontVMetrics(fontInfo, null, d, null);
            descent = d.get(0) * scale;

            ByteBuffer bitmap = memAlloc(BITMAP_W * BITMAP_H);

            STBTTPackContext pc = STBTTPackContext.mallocStack(stack);
            stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL);
            stbtt_PackSetOversampling(pc, 4, 4);
            stbtt_PackFontRange(pc, ttf, 0, FONT_HEIGHT, 32, cdata);
            stbtt_PackEnd(pc);

            // Convert R8 to RGBA8
            ByteBuffer texture = memAlloc(BITMAP_W * BITMAP_H * 4);
            for (int i = 0; i < bitmap.capacity(); i++) {
                texture.putInt((bitmap.get(i) << 24) | 0x00FFFFFF);
            }
            texture.flip();

            glBindTexture(GL_TEXTURE_2D, fontTexID);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, BITMAP_W, BITMAP_H, 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8_REV, texture);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

            memFree(texture);
            memFree(bitmap);
        }

        mDefaultFont
                .width((handle, h, text, len) -> {
                    float text_width = 0;
                    try (MemoryStack stack = stackPush()) {
                        IntBuffer unicode = stack.mallocInt(1);

                        int glyph_len = nnk_utf_decode(text, memAddress(unicode), len);
                        int text_len  = glyph_len;

                        if (glyph_len == 0) {
                            return 0;
                        }

                        IntBuffer advance = stack.mallocInt(1);
                        while (text_len <= len && glyph_len != 0) {
                            if (unicode.get(0) == NK_UTF_INVALID) {
                                break;
                            }

                            /* query currently drawn glyph information */
                            stbtt_GetCodepointHMetrics(fontInfo, unicode.get(0), advance, null);
                            text_width += advance.get(0) * scale;

                            /* offset next glyph */
                            glyph_len = nnk_utf_decode(text + text_len, memAddress(unicode), len - text_len);
                            text_len += glyph_len;
                        }
                    }
                    return text_width;
                })
                .height(FONT_HEIGHT)
                .query((handle, font_height, glyph, codepoint, next_codepoint) -> {
                    try (MemoryStack stack = stackPush()) {
                        FloatBuffer x = stack.floats(0.0f);
                        FloatBuffer y = stack.floats(0.0f);

                        STBTTAlignedQuad q       = STBTTAlignedQuad.mallocStack(stack);
                        IntBuffer        advance = stack.mallocInt(1);

                        stbtt_GetPackedQuad(cdata, BITMAP_W, BITMAP_H, codepoint - 32, x, y, q, false);
                        stbtt_GetCodepointHMetrics(fontInfo, codepoint, advance, null);

                        NkUserFontGlyph ufg = NkUserFontGlyph.create(glyph);

                        ufg.width(q.x1() - q.x0());
                        ufg.height(q.y1() - q.y0());
                        ufg.offset().set(q.x0(), q.y0() + (FONT_HEIGHT + descent));
                        ufg.xadvance(advance.get(0) * scale);
                        ufg.uv(0).set(q.s0(), q.t0());
                        ufg.uv(1).set(q.s1(), q.t1());
                    }
                })
                .texture().id(fontTexID);

        nk_style_set_font(mContext, mDefaultFont);
    }

    public void render() {
        int AA               = NK_ANTI_ALIASING_ON;
        int maxVertexBuffer  = MAX_VERTEX_BUFFER;
        int maxElementBuffer = MAX_ELEMENT_BUFFER;

        int width, height;
        int displayWidth, displayHeight;

        try (MemoryStack stack = stackPush()) {
            IntBuffer wBuf = stack.mallocInt(1);
            IntBuffer hBuf = stack.mallocInt(1);

            glfwGetWindowSize(mWindow, wBuf, hBuf);

            width  = wBuf.get(0);
            height = hBuf.get(0);

            glfwGetFramebufferSize(mWindow, wBuf, hBuf);
            displayWidth  = wBuf.get(0);
            displayHeight = hBuf.get(0);
        }

        try (MemoryStack stack = stackPush()) {
            // setup global state
            glEnable(GL_BLEND);
            glBlendEquation(GL_FUNC_ADD);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glDisable(GL_CULL_FACE);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_SCISSOR_TEST);
            glActiveTexture(GL_TEXTURE0);

            glUseProgram(mProgram);
            glUniform1i(mTextureUniform, 0);
            glUniformMatrix4fv(mProjectionMatrixUniform, false, stack.floats(
                    2.0f / width, 0.0f, 0.0f, 0.0f,
                    0.0f, -2.0f / height, 0.0f, 0.0f,
                    0.0f, 0.0f, -1.0f, 0.0f,
                    -1.0f, 1.0f, 0.0f, 1.0f
            ));
            glViewport(0, 0, width, height);
        }

        {
            glBindVertexArray(mVao);
            glBindBuffer(GL_ARRAY_BUFFER, mVbo);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mEbo);

            glBufferData(GL_ARRAY_BUFFER, maxVertexBuffer, GL_STREAM_DRAW);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, maxElementBuffer, GL_STREAM_DRAW);

            ByteBuffer vertices = Objects.requireNonNull(glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, maxVertexBuffer, null));
            ByteBuffer elements = Objects.requireNonNull(glMapBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_WRITE_ONLY, maxElementBuffer, null));

            try (MemoryStack stack = stackPush()) {
                // fill convert configuration
                NkConvertConfig convertCfg = NkConvertConfig.callocStack(stack)
                        .vertex_layout(VERTEX_LAYOUT)
                        .vertex_size(20)
                        .vertex_alignment(4)
                        .null_texture(mNullTexture)
                        .circle_segment_count(22)
                        .curve_segment_count(22)
                        .arc_segment_count(22)
                        .global_alpha(1.0f)
                        .shape_AA(AA)
                        .line_AA(AA);

                // setup buffers to load vertices and elements
                NkBuffer vtxBuf = NkBuffer.mallocStack(stack);
                NkBuffer elBuf = NkBuffer.mallocStack(stack);

                nk_buffer_init_fixed(vtxBuf, vertices/*, max_vertex_buffer*/);
                nk_buffer_init_fixed(elBuf, elements/*, max_element_buffer*/);

                nk_convert(mContext, mCommandBuffer, vtxBuf, elBuf, convertCfg);
            }

            glUnmapBuffer(GL_ELEMENT_ARRAY_BUFFER);
            glUnmapBuffer(GL_ARRAY_BUFFER);

            // iterate over and execute each draw command
            float fbScaleX = (float) displayWidth / (float)width;
            float fbScaleY = (float) displayHeight / (float)height;

            long offset = NULL;
            for (NkDrawCommand cmd = nk__draw_begin(mContext, mCommandBuffer); cmd != null; cmd = nk__draw_next(cmd, mCommandBuffer, mContext)) {
                if (cmd.elem_count() == 0) {
                    continue;
                }
                glBindTexture(GL_TEXTURE_2D, cmd.texture().id());
                glScissor(
                        (int) (cmd.clip_rect().x() * fbScaleX),
                        (int) ((height - (int)(cmd.clip_rect().y() + cmd.clip_rect().h())) * fbScaleY),
                        (int) (cmd.clip_rect().w() * fbScaleX),
                        (int) (cmd.clip_rect().h() * fbScaleY)
                );
                glDrawElements(GL_TRIANGLES, cmd.elem_count(), GL_UNSIGNED_SHORT, offset);
                offset += cmd.elem_count() * 2;
            }
            nk_clear(mContext);
        }

        // default OpenGL state
        glUseProgram(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        glDisable(GL_BLEND);
        glDisable(GL_SCISSOR_TEST);
    }

    public void destroy() {
        glDetachShader(mProgram, mVertexShader);
        glDetachShader(mProgram, mFragmentShader);
        glDeleteShader(mVertexShader);
        glDeleteShader(mFragmentShader);

        glDeleteProgram(mProgram);

        glDeleteTextures(mDefaultFont.texture().id());
        glDeleteTextures(mNullTexture.texture().id());

        glDeleteBuffers(mVbo);
        glDeleteBuffers(mEbo);

        nk_buffer_free(mCommandBuffer);
    }
}
