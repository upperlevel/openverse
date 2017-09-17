package xyz.upperlevel.openverse.client.render.block;

import com.google.gson.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Vector3f;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class BlockPart {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(BlockPart.class, new Deserializer())
            .create();

    private final Vector3f fromPosition, toPosition;
    private Map<Facing, BlockPartFace> faces = new HashMap<>();

    public static BlockPart deserialize(JsonElement element) {
        return GSON.fromJson(element, BlockPart.class);
    }

    private static class Deserializer implements JsonDeserializer<BlockPart> {
        private Vector3f parsePos(JsonObject json) {
            return new Vector3f(
                    json.get("x").getAsFloat(),
                    json.get("y").getAsFloat(),
                    json.get("z").getAsFloat()
            );
        }

        @Override
        public BlockPart deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = element.getAsJsonObject();
            BlockPart res = new BlockPart(
                    parsePos(json.get("from").getAsJsonObject()),
                    parsePos(json.get("to").getAsJsonObject())
            );
            return res;
        }
    }
}
