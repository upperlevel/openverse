package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import org.joml.AABBf;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.world.block.Block;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
public class BlockPart {
    private final Vector3f from, to, size, position;
    private final AABBf aabb;

    private Map<Facing, BlockPartFace> faces = new HashMap<>();

    private Vector3f parsePos(Config config) {
        return new Vector3f(
                config.getFloat("x"),
                config.getFloat("y"),
                config.getFloat("z")
        );
    }

    private Facing parseFacing(String string) {
        return Facing.valueOf(string.toUpperCase(Locale.ENGLISH).replace("_", " "));
    }

    @SuppressWarnings("unchecked")
    public BlockPart(Config config) {
        from = parsePos(config.getConfigRequired("from"));
        to = parsePos(config.getConfigRequired("to"));
        size = from.sub(to, new Vector3f()).absolute();
        position = new Vector3f(from).min(to);
        aabb = new AABBf(position, new Vector3f(from).max(to));

        for (Map.Entry<String, Object> faceMap : config.getSectionRequired("faces").entrySet()) {
            Facing fac = parseFacing(faceMap.getKey());
            if (fac != null) {
                faces.put(fac, BlockPartFace.deserialize(this, fac, Config.wrap((Map<String, Object>) faceMap.getValue())));
            }
        }
    }

    public int getVerticesCount() {
        return faces.values().size() * 4;
    }

    public int store(Block block, Matrix4f transform, ByteBuffer buffer) {
        transform.translate(size.add(position, new Vector3f()));
        int vt = 0;
        for (BlockPartFace face : faces.values())
            vt += face.store(block, new Matrix4f(transform), buffer);
        return vt;
    }

    public static BlockPart deserialize(Config config) {
        return new BlockPart(config);
    }
}
