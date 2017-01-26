package xyz.upperlevel.opencraft.world.block;

import lombok.*;
import org.joml.Vector3f;

import java.util.*;
import java.util.stream.Collectors;

public class BlockComponent {

    public static final BlockComponent NULL = new BlockComponent() {{
        setZone(new BlockComponentZone());
    }};

    public static final String NULL_ID = "_null";

    private final Map<BlockFacePosition, BlockFace> faces = new HashMap<BlockFacePosition, BlockFace>() {{
        for (BlockFacePosition position : BlockFacePosition.values())
            put(position, new BlockFace(BlockComponent.this, position));
    }};

    @Getter
    private String id = NULL_ID;

    /**
     * The variable transparent is checked on face rendering. If a face
     * is inside a transparent block component then it is considered
     * renderable, otherwise not.
     */
    @Getter
    @Setter
    private boolean transparent = false;

    @Getter
    @Setter
    @NonNull
    private BlockComponentZone zone = new BlockComponentZone(
            new Vector3f(),
            new Vector3f(1f, 1f, 1f)
    );

    public BlockComponent() {
        this(NULL_ID);
        setTransparent(false);
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

    public boolean isInside(BlockComponentZone zone) {
        return this.zone.isInside(zone);
    }

    public boolean isInside(BlockComponent component) {
        return zone.isInside(component.getZone());
    }

    public boolean isInside(BlockFace face) {
        return isInside(face.getZone());
    }

    public BlockFace getFace(BlockFacePosition position) {
        return faces.get(position); // must be present since initialized in the constructor
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

    public final FaceBuffer faceBuf = new FaceBuffer();

    public FaceBuffer getFaceBuffer() {
        return faceBuf;
    }

    public class FaceBuffer { // useful for rendering

        private Set<BlockFace> faces = new HashSet<>(); // using a set because it cannot contain same position faces

        private FaceBuffer() {
        }

        public Set<BlockFace> computeFaces() {
            return computeFaces(NULL);
        }

        public Set<BlockFace> computeFaces(BlockComponent component) {
            // add all visible faces to the buffer
            // should remove faces of the same position by itself
            faces.addAll(BlockComponent.this.getFaces().stream()
                    .filter(face -> face.isVisible(component) || (component.getZone().isFace(face.getZone()) && component.isInside(BlockComponent.this)))
                    .collect(Collectors.toList())
            );
            return faces;
        }

        public Set<BlockFace> getFaces() {
            return faces;
        }
    }
}