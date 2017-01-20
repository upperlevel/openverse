package xyz.upperlevel.opencraft.world.block.face;

import lombok.*;
import org.joml.Vector3f;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BlockComponent {

    public static final Map<BlockFace, BlockFaceData> DEFAULT_FACES = new HashMap<BlockFace, BlockFaceData>() {{
        for (BlockFace face : BlockFace.values())
            put(face, BlockFaceData.NULL);
    }};

    public final Map<BlockFace, BlockFaceData> facesData = new HashMap<>(DEFAULT_FACES);

    @Getter
    @Setter
    @NonNull
    private BlockComponentZone zone;

    public BlockFaceData getFaceData(BlockFace position) {
        return facesData.get(position);
    }

    public void setFaceData(BlockFace position, BlockFaceData data) {
        facesData.put(position, data != null ? data : BlockFaceData.NULL);
    }

    public BlockComponentFace getLeftFace() {
        return new BlockComponentFace(
                this,
                BlockFace.LEFT,
                new BlockComponentZone(
                        new Vector3f(zone.getMinX(), zone.getMinY(), zone.getMinZ()),
                        new Vector3f(zone.getMinX(), zone.getMaxY(), zone.getMaxZ()))
        );
    }

    public BlockComponentFace getRightFace() {
        return new BlockComponentFace(
                this,
                BlockFace.RIGHT,
                new BlockComponentZone(
                        new Vector3f(zone.getMaxX(), zone.getMinY(), zone.getMinZ()),
                        new Vector3f(zone.getMaxX(), zone.getMaxY(), zone.getMaxZ()))
        );
    }

    public BlockComponentFace getDownFace() {
        return new BlockComponentFace(
                this,
                BlockFace.DOWN,
                new BlockComponentZone(
                        new Vector3f(zone.getMinX(), zone.getMinY(), zone.getMinZ()),
                        new Vector3f(zone.getMaxX(), zone.getMinY(), zone.getMaxZ()))
        );
    }

    public BlockComponentFace getUpFace() {
        return new BlockComponentFace(
                this,
                BlockFace.UP,
                new BlockComponentZone(
                        new Vector3f(zone.getMinX(), zone.getMaxY(), zone.getMinZ()),
                        new Vector3f(zone.getMaxX(), zone.getMaxY(), zone.getMaxZ()))
        );
    }

    public BlockComponentFace getBackwardFace() {
        return new BlockComponentFace(
                this,
                BlockFace.BACKWARD,
                new BlockComponentZone(
                        new Vector3f(zone.getMinX(), zone.getMinY(), zone.getMinZ()),
                        new Vector3f(zone.getMaxX(), zone.getMaxX(), zone.getMinZ()))
        );
    }

    public BlockComponentFace getForwardFace() {
        return new BlockComponentFace(
                this,
                BlockFace.FORWARD,
                new BlockComponentZone(
                        new Vector3f(zone.getMinX(), zone.getMinY(), zone.getMaxZ()),
                        new Vector3f(zone.getMaxX(), zone.getMaxY(), zone.getMaxZ()))
        );
    }

    public List<BlockComponentFace> getFaces() {
        return new ArrayList<>(Arrays.asList(
                getUpFace(),
                getDownFace(),
                getLeftFace(),
                getRightFace(),
                getForwardFace(),
                getBackwardFace()
        ));
    }

    public List<BlockComponentFace> getVisibleFaces(BlockComponentZone zone) {
        return getFaces().stream()
                .filter(face -> !zone.isInside(face.zone))
                .collect(Collectors.toList());
    }

    public BlockComponent copy() {
        return new BlockComponent(zone.copy());
    }
}
