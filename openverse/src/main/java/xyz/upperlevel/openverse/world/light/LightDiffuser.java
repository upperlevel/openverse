package xyz.upperlevel.openverse.world.light;

import xyz.upperlevel.openverse.world.BlockFace;
import xyz.upperlevel.openverse.world.LightType;
import xyz.upperlevel.openverse.world.World;

import java.util.ArrayDeque;
import java.util.Queue;

public class LightDiffuser {
    private LightDiffuser() {
    }

    public static void diffuseLight(LightDiffusionField field, int x, int y, int z, LightType lightType) {
        Queue<Integer> bfsQueue = new ArrayDeque<>();

        int i = 0x0F0F0F;
        bfsQueue.add(i);

        solveBfsQueue(bfsQueue, field, x, y, z, lightType);
    }

    private static void solveBfsQueue(Queue<Integer> bfsQueue, LightDiffusionField field, int x, int y, int z, LightType lightType) {
        while (!bfsQueue.isEmpty()) {
            int j = bfsQueue.poll();
            int lx = (j & 0xFF0000) >> 16;
            int ly = (j & 0x00FF00) >> 8;
            int lz = (j & 0xFF);
            int wx = lx - 15 + x;
            int wy = ly - 15 + y;
            int wz = lz - 15 + z;
            int lt = field.getLightAt(wx, wy, wz, lightType);

            for (BlockFace face : BlockFace.values()) {
                int rlx = lx + face.offsetX;
                int rly = ly + face.offsetY;
                int rlz = lz + face.offsetZ;

                if ((rlx >= 0 && rlx <= 31) && (rly >= 0 && rly <= 31) && (rlz >= 0 && rlz <= 31)) {
                    int rwx = wx + face.offsetX;
                    int rwy = wy + face.offsetY;
                    int rwz = wz + face.offsetZ;

                    if ((field.getLightAt(rwx, rwy, rwz, lightType) + 2) <= lt) {
                        int rlt = lt - 1;
                        field.setLightAt(rwx, rwy, rwz, rlt, lightType);
                        bfsQueue.add((rlx & 0xFF) << 16 | (rly & 0xFF) << 8 | (rlz & 0xFF));
                    }
                }
            }
        }
    }

    public static void removeLight(LightDiffusionField field, int x, int y, int z, LightType lightType) {
        Queue<Integer> bfsQueue = new ArrayDeque<>();
        Queue<Integer> bfsRemoveQueue = new ArrayDeque<>();

        int i = 0x0F0F0F00 | (field.getLightAt(x, y, z, lightType) & 0x0F);
        field.setLightAt(x, y, z, 0, lightType);
        bfsRemoveQueue.add(i);

        while (!bfsRemoveQueue.isEmpty()) {
            i = bfsRemoveQueue.poll();
            int cx = (i & 0xFF000000) >> 24;
            int cy = (i & 0x00FF0000) >> 16;
            int cz = (i & 0x0000FF00) >> 8;
            int cl = (i & 0x0F);

            int cwx = cx - 15 + x;
            int cwy = cy - 15 + y;
            int cwz = cz - 15 + z;

            for (BlockFace r : BlockFace.values()) {
                int nlx = cx + r.offsetX;
                int nly = cy + r.offsetY;
                int nlz = cz + r.offsetZ;

                if ((nlx >= 0 && nlx <= 31) && (nly >= 0 && nly <= 31) && (nlz >= 0 && nlz <= 31)) {
                    int nwx = cwx + r.offsetX;
                    int nwy = cwy + r.offsetY;
                    int nwz = cwz + r.offsetZ;
                    int nll = field.getLightAt(nwx, nwy, nwz, lightType);

                    int ni = (nlx & 0xFF) << 24 | (nly & 0xFF) << 16 | (nlz & 0xFF) << 8 | nll & 0x0F;
                    if (nll != 0 && nll < cl) {
                        field.setLightAt(nwx, nwy, nwz, 0, lightType);
                        bfsRemoveQueue.add(ni);
                    } else if (nll >= cl) {
                        bfsQueue.add(ni);
                    }
                }
            }
        }

        for (int j : bfsQueue) {
            int lx = (j & 0xFF0000) >> 24;
            int ly = (j & 0x00FF00) >> 16;
            int lz = (j & 0xFF) >> 8;
            int wx = lx - 15 + x;
            int wy = ly - 15 + y;
            int wz = lz - 15 + z;
            diffuseLight(field, wx, wy, wz, lightType);
        }
    }
}
