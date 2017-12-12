package xyz.upperlevel.openverse.server.world.chunk;

import org.junit.Assert;
import org.junit.Test;
import xyz.upperlevel.openverse.console.ChatColor;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

public class ChunkMapTest {
    @Test
    public void chunkMapTest() {
        TestChunkMap map = new TestChunkMap(1, 11, 11, 11);
        map.spawn(5, 5, 5);

        map.move(0, 1, 0);
        map.move(0, 0, 1);
        map.move(1, 0, 0);
        map.move(7, 0, 0);
        map.move(-8, 10, 0);
        map.move(0, -11, -1);

        map.despawn();
    }

    public static class TestChunkMap extends ChunkMapHandler {
        private final int width, height, length;
        private final boolean[][] sentChunkPillars;
        private final boolean[][][] sentChunks;

        private ChunkLocation current;

        public TestChunkMap(int radius, int width, int height, int length) {
            super(radius);
            this.width = width;
            this.height = height;
            this.length = length;
            sentChunkPillars = new boolean[width][length];
            sentChunks = new boolean[width][height][length];
        }

        private void fail() {
            System.out.println("Chunks on XY:");
            printChunksOnXY();

            System.out.println("Chunks on XZ:");
            printChunksOnXZ();

            System.out.println("Chunk pillars (on XZ):");
            printChunkPillars();

            Assert.fail();
        }

        public void changeRadius(int radius) {
            setRadius(null, current, radius);
            if (!isCorrect()) {
                fail();
            }
        }

        public void spawn(int x, int y, int z) {
            current = new ChunkLocation(x, y, z);
            addChunkView(null, current);
            if (!isCorrect()) {
                fail();
            }
        }

        public void move(int xOff, int yOff, int zOff) {
            if (current == null)
                throw new IllegalArgumentException("Not spawned");
            ChunkLocation loc = new ChunkLocation(current.x + xOff, current.y + yOff, current.z + zOff);
            moveChunkView(null, current, loc);
            current = loc;
            if (!isCorrect()) {
                fail();
            }
        }

        public void despawn() {
            if (current == null)
                throw new IllegalStateException("Not spawned");
            removeChunkView(null, current);
            current = null;
            if (!isEmpty()) {
                fail();
            }
        }

        public boolean isCorrect() {
            if (current == null)
                throw new IllegalStateException("Not spawned");
            int rad = getRadius();
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < length; z++) {
                    if (x >= current.x - rad && x <= current.x + rad && z >= current.z - rad && z <= current.z + rad) {
                        if (!sentChunkPillars[x][z]) {
                            return false;
                        }
                        for (int y = 0; y < height; y++) {
                            if (y >= current.y - rad && y <= current.y + rad) {
                                if (!sentChunks[x][y][z]) {
                                    printChunksOnXY();
                                    return false;
                                }
                            }
                        }
                    } else {
                        if (sentChunkPillars[x][z]) {
                            return false;
                        }
                        for (int y = 0; y < height; y++) {
                            if (y < current.y - rad || y > current.y + rad) {
                                if (sentChunks[x][y][z]) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

        public boolean isEmpty() {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < length; z++) {
                    if (sentChunkPillars[x][z]) {
                        return false;
                    }
                    for (int y = 0; y < height; y++) {
                        if (sentChunks[x][y][z]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        // Here the world is not infinite :(
        private boolean isOut(int x, int z) {
            return x < 0 || x >= width || z < 0 || z >= length;
        }

        private boolean isOut(int x, int y, int z) {
            return x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= length;
        }

        @Override
        public void onChunkPillarAdd(ServerPlayer player, int x, int z) {
            if (isOut(x, z))
                return;
            sentChunkPillars[x][z] = true;
        }

        @Override
        public void onChunkPillarRemove(ServerPlayer player, int x, int z) {
            if (isOut(x, z))
                return;
            sentChunkPillars[x][z] = false;
        }

        @Override
        public void onChunkAdd(ServerPlayer player, int x, int y, int z) {
            if (isOut(x, y, z))
                return;
            sentChunks[x][y][z] = true;
        }

        @Override
        public void onChunkRemove(ServerPlayer player, int x, int y, int z) {
            if (isOut(x, y, z))
                return;
            sentChunks[x][y][z] = false;
        }

        public void printChunkPillars() {
            for (int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {
                    if (sentChunkPillars[x][z]) {
                        System.out.print(ChatColor.GREEN + "█ " + ChatColor.RESET);
                    } else {
                        System.out.print("█ ");
                    }
                }
                System.out.println();
            }
        }

        public void printChunksOnXY() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (!isOut(x, y, current.z) && sentChunks[x][y][current.z]) {
                        System.out.print(ChatColor.GREEN + "█ " + ChatColor.RESET);
                    } else {
                        System.out.print("█ ");
                    }
                }
                System.out.println();
            }
        }

        public void printChunksOnXZ() {
            for (int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {
                    if (!isOut(x, current.y, z) && sentChunks[x][current.y][z]) {
                        System.out.print(ChatColor.GREEN + "█ " + ChatColor.RESET);
                    } else {
                        System.out.print("█ ");
                    }
                }
                System.out.println();
            }
        }
    }
}
