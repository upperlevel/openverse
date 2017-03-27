package xyz.upperlevel.opencraft.client.asset.shape;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.resource.model.Cube;
import xyz.upperlevel.opencraft.client.resource.model.ModelPart;
import xyz.upperlevel.opencraft.client.view.WorldView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BlockShape {

    @Getter
    private String id;

    @Getter
    private List<ModelPart> components = new ArrayList<>();

    @Getter
    private boolean transparent = false;

    public BlockShape(@NonNull String id) {
        this.id = id;
    }

    public BlockShape setTransparent(boolean transparent) {
        this.transparent = transparent;
        return this;
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    public BlockShape add(ModelPart component) {
        components.add(component);
        return this;
    }

    public BlockShape add(ModelPart... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    public BlockShape add(Collection<ModelPart> components) {
        this.components.addAll(components);
        return this;
    }

    public BlockShape remove(ModelPart component) {
        components.remove(component);
        return this;
    }

    public BlockShape remove(Collection<ModelPart> components) {
        this.components.removeAll(components);
        return this;
    }

    public int compile(Matrix4f matrix, ByteBuffer buffer) {
        int vertices = 0;
        for (ModelPart component : components) {
            vertices += component.compile(new Matrix4f(matrix), buffer);
        }
        return vertices;
    }

    public int cleanCompile(int x, int y, int z, WorldView area, Matrix4f matrix, ByteBuffer buffer) {
        int vertices = 0;
        for (ModelPart component : components) {
            vertices += component.cleanCompile(x, y, z, area, matrix, buffer);
        }
        return vertices;
    }

    public boolean isInside(float x, float y, float z) {
        return components.stream()
                .anyMatch(comp -> comp.isInside(x, y, z));
    }

    public boolean isInside(Zone3f zone) {
        return components.stream()
                .anyMatch(comp -> comp.isInside(zone));
    }

    public int getVerticesCount() {
        return components.size() * Cube.VERTICES_COUNT;
    }

    public int getDataCount() {
        return components.size() * Cube.DATA_COUNT;
    }
}
