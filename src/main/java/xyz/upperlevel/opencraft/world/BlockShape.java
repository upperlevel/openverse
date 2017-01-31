package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.render.VertexBuffer;

import java.util.*;

public class BlockShape {

    @Getter
    public final List<BlockComponent> components = new ArrayList<>();

    public BlockShape() {
    }

    public BlockShape addComponent(BlockComponent component) {
        components.add(component);
        return this;
    }

    public BlockShape addComponents(Collection<BlockComponent> components) {
        this.components.addAll(components);
        return this;
    }

    public BlockShape removeComponent(BlockComponent component) {
        components.remove(component);
        return this;
    }

    public BlockShape removeComponents(Collection<BlockComponent> components) {
        this.components.removeAll(components);
        return this;
    }

    public static BlockShape empty() {
        return new BlockShape();
    }

    protected int compile(VertexBuffer buffer, Matrix4f matrix) {
        int vertices = 0;
        for (BlockComponent component : components)
            vertices += component.compile(buffer, new Matrix4f(matrix));
        return vertices;
    }
}
