package xyz.upperlevel.opencraft.world.block;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class BlockFace {

    @Getter
    public final BlockComponent component;

    @Getter
    public final BlockFacePosition position;

    @Getter
    private FaceData data = FaceData.NULL;

    public BlockFace(BlockComponent component, BlockFacePosition position) {
        this.component = component;
        this.position = position;
    }

    public BlockFace(BlockComponent component, BlockFacePosition position, FaceData data) {
        this(component, position);
        this.data = data;
    }

    public void setData(FaceData data) {
        this.data = data != null ? data : FaceData.NULL;
    }

    public boolean isVisible(BlockComponentZone zone) {
        return !zone.isInside(getZone());
    }

    public BlockComponentZone getZone() {
        return position.getZone(component.getZone());
    }

    public void load(Map<String, Object> data) {
        // todo retrieves data from a data registry
    }

    public Map<String, Object> save() {
        Map<String, Object> data = new HashMap<>();
        data.put("data", this.data.save());
        return data;
    }

    public BlockFace copy() {
        return new BlockFace(component, position, data.copy());
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BlockFace && position == ((BlockFace) object).position;
    }
}
