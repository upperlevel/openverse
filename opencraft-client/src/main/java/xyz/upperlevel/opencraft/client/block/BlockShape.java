package xyz.upperlevel.opencraft.client.block;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Accessors(chain = true)
public class BlockShape {

    @Getter
    private int verticesCount;

    @Getter
    private final List<BlockShapeComponent> components = new ArrayList<>();

    /**
     * A shape is considered bulky when it has at least one component with a zone that goes
     * from coordinates lower (or equal) than 0 to coordinates higher (or equal) than 1. Simply
     * if the zone is at least larger as a block and contains its area.
     */

    @Getter
    @Setter
    private boolean bulky = false;

    /**
     * A shape is considered transparent only and solely if permits seeing relative blocks
     * through it. In this case they would be rendered though.
     */

    @Getter
    @Setter
    private boolean transparent = false;

    public BlockShape() {
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    @Deprecated
    public void computeBulky() {
        for (BlockShapeComponent component : components) {
            Zone3f zone = component.getZone();
            Vector3f position;
            position = zone.getMinPosition();
            if (position.x <= 0 && position.y <= 0 && position.z <= 0) {
                position = zone.getMaxPosition();
                if (position.x >= 1 && position.y >= 1 && position.z >= 1) {
                    bulky = true;
                    return;
                }
            }
        }
        bulky = false;
    }

    /**
     * A shape is considered occluding when it could hide completely a relative block along
     * its relative direction axis. Depends on bulky and transparency.
     */

    public boolean isOccluding() {
        return bulky && !transparent;
    }

    public BlockShape addComponent(BlockShapeComponent component) {
        components.add(component);
        return this;
    }

    public BlockShape addComponents(Collection<BlockShapeComponent> components) {
        this.components.addAll(components);
        return this;
    }

    public BlockShape removeComponent(BlockShapeComponent component) {
        components.remove(component);
        return this;
    }

    public BlockShape removeComponents(Collection<BlockShapeComponent> components) {
        this.components.removeAll(components);
        return this;
    }

    public int getVerticesCount() {
        return components.size() * BlockShapeComponent.VERTICES_COUNT;
    }

    public int getDataCount() {
        return components.size() * BlockShapeComponent.DATA_COUNT;
    }

    public static BlockShape empty() {
        return new BlockShape();
    }

    public void compileBuffer(ByteBuffer buffer, Matrix4f matrix) {
        for (BlockShapeComponent component : components)
            component.compileBuffer(buffer, new Matrix4f(matrix));
    }
}
