package xyz.upperlevel.openverse.client.render.block;

import com.google.gson.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Vector2f;

import java.lang.reflect.Type;

@Getter
@RequiredArgsConstructor
public class BlockPartFace {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(BlockPartFace.class, new Deserializer())
            .create();

    private final String texturePath;
    private final Vector2f fromUv, toUv;

    public static BlockPartFace deserialize(JsonElement element) {
        return GSON.fromJson(element, BlockPartFace.class);
    }

    private static class Deserializer implements JsonDeserializer<BlockPartFace> {
        private Vector2f parseUv(JsonObject json) {
            return new Vector2f(
                    json.get("u").getAsFloat(),
                    json.get("v").getAsFloat()
            );
        }

        @Override
        public BlockPartFace deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }
    }
}
