package xyz.upperlevel.opencraft.world.block;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

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

    public boolean isVisible(BlockComponent component) {
        out.println("---");
        out.println(this.component.getId() + ">" + position.name() + "> is component transparent? " + component.isTransparent());
        out.println(this.component.getId() + ">" + position.name() + "> is inside component? " + component.isInside(this));
        out.println(this.component.getId() + ">" + position.name() + "> visible? " + (component.isTransparent() || !component.isInside(this)));
        return component.isTransparent() || !component.isInside(this);
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
