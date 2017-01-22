package xyz.upperlevel.opencraft.world.block;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

public class BlockComponent {

    public static final String NULL_ID = "_null";

    private final Map<BlockFacePosition, BlockFace> faces = new HashMap<BlockFacePosition, BlockFace>() {{
        for (BlockFacePosition position : BlockFacePosition.values())
            put(position, new BlockFace(BlockComponent.this, position));
    }};

    @Getter
    private String id = NULL_ID;

    @Getter
    @Setter
    @NonNull
    private BlockComponentZone zone = new BlockComponentZone();

    public BlockComponent() {
        this(NULL_ID);
    }

    public BlockComponent(String id) {
        this.id = id != null ? id : NULL_ID;
    }

    public BlockComponent(String id, BlockComponentZone zone) {
        this(id);
        this.zone = zone;
    }

    public boolean hasId() {
        return id.equals(NULL_ID);
    }

    public BlockFace getFace(BlockFacePosition position) {
        return faces.get(position); // must be present since initialized in the constructor
    }

    public Collection<BlockFace> getVisibleFaces(BlockComponentZone zone) {
        return getFaces().stream()
                .filter(face -> face.isVisible(zone))
                .collect(Collectors.toList());
    }

    public Collection<BlockFace> getFaces() {
        return faces.values();
    }

    @SuppressWarnings("unchecked")
    public void load(Map<String, Object> data) {
        id = (String) data.get("id");
    }

    public Map<String, Object> save() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        faces.forEach((position, face) -> {
            Map<String, Object> subData = new HashMap<>();
            subData.put(position.name(), face.save());
            data.put("faces", subData);
        });
        return data;
    }

    public BlockComponent copy() {
        BlockComponent copied = new BlockComponent(id, zone.copy());
        faces.values().stream()
                .map(BlockFace::copy)
                .collect(Collectors.toList())
                .forEach(face -> copied.faces.put(face.position, face));
        return copied;
    }
}