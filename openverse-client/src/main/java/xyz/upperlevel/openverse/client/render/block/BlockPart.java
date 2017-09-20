package xyz.upperlevel.openverse.client.render.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.util.config.Config;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BlockPart {
    private final Vector3f from, to, size, position;
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

        for (Map.Entry<String, Object> faceMap : config.getSectionRequired("faces").entrySet()) {
            Facing fac = parseFacing(faceMap.getKey());
            if (fac != null) {
                faces.put(fac, BlockPartFace.deserialize(fac, Config.wrap((Map<String, Object>) faceMap.getValue())));
            }
        }
    }

    public int getVerticesCount() {
        return faces.values().size() * 4;
    }

    public void store(Matrix4f transform, ByteBuffer buffer) {
        transform.translate(size.add(position));
        for (BlockPartFace face : faces.values())
            face.store(new Matrix4f(transform), buffer);
    }

    public static BlockPart deserialize(Config config) {
        return new BlockPart(config);
    }
}
