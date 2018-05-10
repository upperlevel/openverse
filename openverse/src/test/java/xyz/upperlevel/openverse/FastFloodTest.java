package xyz.upperlevel.openverse;

import org.junit.Test;
import xyz.upperlevel.openverse.console.ChatColor;
import xyz.upperlevel.openverse.util.math.bfs.FastFloodContext;
import xyz.upperlevel.openverse.util.math.bfs.FastFloodAlgorithm;

public class FastFloodTest {
    // Propagation check for a value of 1
    public static final int[][] PROPAGATION_CHECK_1 = {
            {1}
    };

    // Propagation check for a value of 3
    public static final int[][] PROPAGATION_CHECK_3 = {
            {0, 0, 1, 0, 0},
            {0, 1, 2, 1, 0},
            {1, 2, 3, 2, 1},
            {0, 1, 2, 1, 0},
            {0, 0, 1, 0, 0},
    };

    // Propagation check for a value of 5
    public static final int[][] PROPAGATION_CHECK_5 = {
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 2, 1, 0, 0, 0},
            {0, 0, 1, 2, 3, 2, 1, 0, 0},
            {0, 1, 2, 3, 4, 3, 2, 1, 0},
            {1, 2, 3, 4, 5, 4, 3, 2, 1},
            {0, 1, 2, 3, 4, 3, 2, 1, 0},
            {0, 0, 1, 2, 3, 2, 1, 0, 0},
            {0, 0, 0, 1, 2, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0}
    };

    private static boolean checkArray2d(int[][] first, int[][] second) {
        if (first.length != second.length) {
            return false;
        }
        for (int x = 0; x < first.length; x++) {
            if (first[x].length != second[x].length) {
                return false;
            }
            for (int y = 0; y < first[x].length; y++) {
                if (first[x][y] != second[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks in three stages if the propagation algorithm works at well.
     */
    @Test
    public void checkPropagation() {
        FastFloodAlgorithm ff = new FastFloodAlgorithm();
        TestContext ctx;

        // Propagation for value of 1
        ctx = new TestContext(1, 1, 1);
        ff.addNode(0, 0, 0, 1)
                .start(ctx);
        if (!checkArray2d(PROPAGATION_CHECK_1, ctx.field[0])) {
            System.out.println(ctx.buildYzPane(0));
            throw new AssertionError("Propagation check 1 not passed");
        }

        // Propagation for value of 3
        ctx = new TestContext(5, 5, 5);
        ff.addNode(2, 2, 2, 3)
                .start(ctx);
        if (!checkArray2d(PROPAGATION_CHECK_3, ctx.field[2])) {
            System.out.println(ctx.buildYzPane(2));
            throw new AssertionError("Propagation check 2 not passed");
        }

        // Propagation for value of 5
        ctx = new TestContext(9, 9, 9);
        ff.addNode(4, 4, 4, 5)
                .start(ctx);
        if (!checkArray2d(PROPAGATION_CHECK_5, ctx.field[4])) {
            System.out.println(ctx.buildYzPane(4));
            throw new AssertionError("Propagation check 3 not passed");
        }
    }

    /**
     * Checks if the propagation removal algorithm works at well.
     */
    @Test
    public void checkPropagationRemoval() {
        FastFloodAlgorithm ff = new FastFloodAlgorithm();
        TestContext ctx;

        // Propagation for value of 3
        ctx = new TestContext(5, 5, 5);
        ff.addNode(2, 2, 2, 3)
                .start(ctx);
        if (!checkArray2d(PROPAGATION_CHECK_3, ctx.field[2])) {
            System.out.println(ctx.buildYzPane(2));
            throw new AssertionError("Propagation check not passed");
        }

        // Propagation removal for the old value of 5
        ff.addRemovalNode(2, 2, 2, 3)
                .start(ctx);
        if (!checkArray2d(new int[5][5], ctx.field[2])) {
            System.out.println(ctx.buildYzPane(2));
            throw new AssertionError("Propagation removal not passed");
        }
    }


    /**
     * A class that provides a context on which set and get values.
     * Normally, in Openverse, this is done by the world that provides light values (for example).
     */
    private static class TestContext implements FastFloodContext {
        private int width, height, length;
        private int field[][][];

        public TestContext(int width, int height, int length) {
            this.width = width;
            this.height = height;
            this.length = length;
            field = new int[width][height][length];
        }

        @Override
        public boolean isOutOfBounds(int x, int y, int z) {
            return x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= length;
        }

        @Override
        public int getValue(int x, int y, int z) {
            return field[x][y][z];
        }

        @Override
        public void setValue(int x, int y, int z, int value) {
            field[x][y][z] = value;
        }

        @Override
        public boolean isOpaque(int x, int y, int z) {
            return false;
        }

        public String buildYzPane(int x) {
            StringBuilder str = new StringBuilder();
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    int val = field[x][y][z];
                    if (val != 0)
                        str.append(ChatColor.RED);
                    str.append("[");
                    if (val / 10 == 0)
                        str.append("0");
                    str.append(val)
                            .append("]");
                    if (val != 0)
                        str.append(ChatColor.RESET);
                }
                str.append("\n");
            }
            return str.toString();
        }
    }
}
