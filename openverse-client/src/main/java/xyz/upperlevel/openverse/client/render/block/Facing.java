package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joml.Vector3f;
import org.joml.Vector3i;

@Getter
@RequiredArgsConstructor
public enum Facing {
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    FRONT(0, 0, 1),
    BACK(0, 0, -1),
    LEFT(-1, 0, 0),
    RIGHT(1, 0, 0);

    public final int dirX, dirY, dirZ;

    public Vector3i getDir() {
        return new Vector3i(dirX, dirY, dirZ);
    }
}
