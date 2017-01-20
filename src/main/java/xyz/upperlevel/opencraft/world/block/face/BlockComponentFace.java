package xyz.upperlevel.opencraft.world.block.face;

import lombok.Getter;

public class BlockComponentFace {

    @Getter
    public final BlockComponent blockComponent;

    @Getter
    public final BlockFace position;

    @Getter
    public final BlockComponentZone zone;

    public BlockComponentFace(BlockComponent blockComponent, BlockFace position, BlockComponentZone zone) {
        if (!zone.is2D())
            throw new IllegalArgumentException("The zone passed must be a face");
        this.blockComponent = blockComponent;
        this.position = position;
        this.zone = zone;
    }

    public BlockFaceData getData() {
        return blockComponent.getFaceData(position);
    }

    public void setData(BlockFaceData data) {
        blockComponent.setFaceData(position, data);
    }
}
