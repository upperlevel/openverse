package xyz.upperlevel.opencraft;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.junit.Test;

public class LatencyTest {

    @Test
    public void testWorldRenderingLoops() {
        int renderDistance = 1;
        long startAt = System.currentTimeMillis();
        // foreach chunk in render area
        for (int chunkX = -renderDistance; chunkX <= renderDistance; chunkX++) {
            for (int chunkY = -renderDistance; chunkY <= renderDistance; chunkY++) {
                for (int chunkZ = -renderDistance; chunkZ <= renderDistance; chunkZ++) {
                    // foreach block in chunk
                    for (int x = 0; x < 16; x++) {
                        for (int y = 0; y < 16; y++) {
                            for (int z = 0; z < 16; z++) {
                                // do something
                                System.out.println("rendering at chunk:{" + chunkX + "," + chunkY + "," + chunkZ + "} block:{" + x + "," + y + "," + "z" + "}");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Time take to test world rendering: " + (System.currentTimeMillis() - startAt));
    }

    // todo test
    public static void main(String[] args) {
        Matrix4f m = new Matrix4f();
        m.m00(2);
        m.m01(3);
        m.m12(5);
        m.m21(8);

        Vector4f v = new Vector4f();
        v.x = 2;
        v.y = 3;
        v.z = 1;
        v.w = 1;

        System.out.println("result vector: " + v.mul(m).toString());
    }
}
