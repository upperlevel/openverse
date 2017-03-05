package xyz.upperlevel.opencraft.client.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.render.RenderArea;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Accessors(chain = true)
public class BlockShape {

    @Getter
    private String id;

    @Getter
    private List<BlockShapeComponent> components = new ArrayList<>();

    @Getter
    @Setter
    private boolean transparent = false;

    public BlockShape(@NonNull String id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    public BlockShape add(BlockShapeComponent component) {
        components.add(component);
        return this;
    }

    public BlockShape add(BlockShapeComponent... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    public BlockShape add(Collection<BlockShapeComponent> components) {
        this.components.addAll(components);
        return this;
    }

    public BlockShape remove(BlockShapeComponent component) {
        components.remove(component);
        return this;
    }

    public BlockShape remove(Collection<BlockShapeComponent> components) {
        this.components.removeAll(components);
        return this;
    }

    public void compile(Matrix4f matrix, ByteBuffer buffer) {
        components.forEach(component -> component.compile(new Matrix4f(matrix), buffer));
    }

    public void cleanCompile(int x, int y, int z, RenderArea area, Matrix4f matrix, ByteBuffer buffer) {
        components.forEach(component -> component.cleanCompile(x, y, z, area, matrix, buffer));
    }

    public boolean isInside(Zone3f zone) {
        return components.stream()
                .anyMatch(comp -> comp.isInside(zone));
    }

    public int getVerticesCount() {
        return components.size() * BlockShapeComponent.VERTICES_COUNT;
    }

    public int getDataCount() {
        return components.size() * BlockShapeComponent.DATA_COUNT;
    }
}
