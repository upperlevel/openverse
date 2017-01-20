package xyz.upperlevel.opencraft.world.block.shape;

import lombok.Getter;

public class BlockFace {

    @Getter
    public final BlockComponent component;

    @Getter
    public final BlockFacePosition position;

    @Getter
    private BlockFaceData data = BlockFaceData.NULL;

    public BlockFace(BlockComponent component, BlockFacePosition position) {
        this.component = component;
        this.position = position;
    }

    public BlockFace(BlockComponent component, BlockFacePosition position, BlockFaceData data) {
        this(component, position);
        this.data = data;
    }

    public void setData(BlockFaceData data) {
        this.data = data != null ? data : BlockFaceData.NULL;
    }

    public boolean isVisible(BlockComponentZone zone) {
        return !zone.isInside(getZone());
    }

    public BlockComponentZone getZone() {
        return position.getZone(component.getZone());
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
