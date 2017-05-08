package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.ulge.opengl.shader.Uniform;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

import static org.lwjgl.BufferUtils.createFloatBuffer;

public class ClientWorld extends World {

    @Getter
    @Setter
    private PlayerChunkMap playerChunkMap;

    public ClientWorld(String name, int radius) {
        super(name);
        this.playerChunkMap = new RadiusSquareChunkChooser(this, radius);
    }

    public ClientWorld(String name, PlayerChunkMap playerChunkMap) {
        super(name);
        this.playerChunkMap = playerChunkMap;
    }

    public void setCenter(int x, int y, int z) {
        playerChunkMap.setCenter(x, y, z);
    }

    @Override
    public BufferedChunk getChunk(int x, int y, int z) {
        return (BufferedChunk) super.getChunk(x, y, z);
    }

    public void render(Uniformer uniformer) {
        Uniform uModel = uniformer.get("model");

        for(BufferedChunk chunk : playerChunkMap.getChunks()) {
            Matrix4f in = new Matrix4f().translate(16 * chunk.getX(), 16 * chunk.getY(), 16 * chunk.getZ());
            uModel.matrix4(in.get(createFloatBuffer(16)));

            chunk.render();
        }
    }
}
