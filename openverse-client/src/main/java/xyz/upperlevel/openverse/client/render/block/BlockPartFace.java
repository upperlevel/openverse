package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import xyz.upperlevel.openverse.util.config.Config;

import java.nio.ByteBuffer;

@Getter
@RequiredArgsConstructor
public class BlockPartFace {
    private final Facing facing;
    private final String texturePath;
    private final Vector2f fromUv, toUv;

    public BlockPartFace(Facing facing, Config config) {
        this.facing = facing;
        this.texturePath = config.getString("texture");
        fromUv = null;
        toUv = null;
    }

    public void store(Matrix4f in, ByteBuffer buffer) {
    }

    public static BlockPartFace deserialize(Facing facing, Config config) {
        return new BlockPartFace(facing, config);
    }
}
