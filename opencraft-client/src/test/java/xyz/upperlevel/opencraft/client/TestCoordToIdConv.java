package xyz.upperlevel.opencraft.client;

import org.junit.Test;
import xyz.upperlevel.opencraft.client.render.RenderChunk;

public class TestCoordToIdConv {

    @Test
    public void testConv() {
        short id = 0xf0a;
        System.out.println("id=" + id);

        int x = RenderChunk.getX(id);
        System.out.println("x(15)=" + x);
        assert x == 15 : "x != 15";

        int y = RenderChunk.getY(id);
        System.out.println("y(0)=" + y);
        assert y == 0 : "y != 0";

        int z = RenderChunk.getZ(id);
        System.out.println("z(10)=" + z);
        assert z == 10 : "z != 10";

        int newId = RenderChunk.getId(x, y, z);
        System.out.println("id(3850)=" + newId);
        assert newId == id : "id != 0xf0a";
    }
}
