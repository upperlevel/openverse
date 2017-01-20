package xyz.upperlevel.opencraft.world.block.shape;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BlockComponent {

    private final Map<BlockFacePosition, BlockFace> faces = new HashMap<BlockFacePosition, BlockFace>() {{
        for (BlockFacePosition position : BlockFacePosition.values())
            put(position, new BlockFace(BlockComponent.this, position));
    }};

    @Getter
    @Setter
    @NonNull
    private BlockComponentZone zone;

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

    public BlockComponent copy() {
        BlockComponent copied = new BlockComponent(zone.copy());
        faces.values().stream()
                .map(BlockFace::copy)
                .collect(Collectors.toList())
                .forEach(face -> copied.faces.put(face.position, face));
        return copied;
    }
}