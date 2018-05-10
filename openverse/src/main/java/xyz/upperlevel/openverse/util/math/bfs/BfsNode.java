package xyz.upperlevel.openverse.util.math.bfs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BfsNode {
    public int x, y, z;
    public int value;

    public BfsNode(int x, int y, int z, int value) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.value = value;
    }
}
