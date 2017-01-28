package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
<<<<<<< HEAD:src/main/java/xyz/upperlevel/opencraft/world/ChunkRelative.java
public enum ChunkRelative {
=======
public enum Relative {
>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026:src/main/java/xyz/upperlevel/opencraft/world/Relative.java

    UP(0, 1, 0),
    DOWN(0, -1, 0),
    LEFT(-1, 0, 0),
    RIGHT(1, 0, 0),
    FORWARD(0, 0, 1),
    BACKWARD(0, 0, -1);

    @Getter
    public final int offsetX, offsetY, offsetZ;
}