package xyz.upperlevel.opencraft.world.block;

import lombok.Getter;
import org.joml.Vector3f;

public class BlockComponentFace extends BlockComponentZone {

    @Getter
    public final BlockFace position;

    public BlockComponentFace(Vector3f firstPosition, Vector3f secondPosition, BlockFace position) {
        super(firstPosition, secondPosition);
        this.position = position;
    }
}
