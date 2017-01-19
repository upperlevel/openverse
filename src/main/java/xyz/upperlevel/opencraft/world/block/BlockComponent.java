package xyz.upperlevel.opencraft.world.block;

import org.joml.Vector3f;

import java.util.*;
import java.util.stream.Collectors;

public class BlockComponent extends BlockComponentZone {

    public final Map<BlockFace, BlockFaceData> facesData = new HashMap<>();

    public BlockComponent() {
    }

    public void specifyData(BlockFace face, BlockFaceData data) {
        facesData.put(face, data);
    }

    public BlockComponentFace getUpFace() {
        return new BlockComponentFace(
                new Vector3f(getMinX(), getMaxY(), getMinZ()),
                new Vector3f(getMaxX(), getMaxY(), getMaxZ()),
                BlockFace.UP
        );
    }

    public BlockComponentFace getDownFace() {
        return new BlockComponentFace(
                new Vector3f(getMinX(), getMinY(), getMinZ()),
                new Vector3f(getMaxX(), getMinY(), getMaxZ()),
                BlockFace.DOWN
        );
    }

    public BlockComponentFace getLeftFace() {
        return new BlockComponentFace(
                new Vector3f(getMinX(), getMinY(), getMinZ()),
                new Vector3f(getMinX(), getMaxX(), getMaxZ()),
                BlockFace.LEFT
        );
    }

    public BlockComponentFace getRightFace() {
        return new BlockComponentFace(
                new Vector3f(getMaxX(), getMinY(), getMinZ()),
                new Vector3f(getMaxX(), getMaxX(), getMaxZ()),
                BlockFace.RIGHT
        );
    }

    public BlockComponentFace getForwardFace() {
        return new BlockComponentFace(
                new Vector3f(getMinX(), getMinY(), getMaxZ()),
                new Vector3f(getMaxX(), getMaxX(), getMaxZ()),
                BlockFace.FORWARD
        );
    }

    public BlockComponentFace getBackwardFace() {
        return new BlockComponentFace(
                new Vector3f(getMinX(), getMinY(), getMinZ()),
                new Vector3f(getMaxX(), getMaxX(), getMinZ()),
                BlockFace.BACKWARD
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
                .filter(face -> !zone.isInside(face))
                .collect(Collectors.toList());
    }
}
