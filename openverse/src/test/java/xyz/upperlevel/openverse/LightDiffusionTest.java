package xyz.upperlevel.openverse;

import org.junit.Test;
import xyz.upperlevel.openverse.world.LightType;
import xyz.upperlevel.openverse.world.light.LightDiffuser;
import xyz.upperlevel.openverse.world.light.LightDiffusionField;

import static java.lang.System.out;

public class LightDiffusionTest {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private void generateField(LightFieldTest lightFieldTest) {

    }

    @Test
    public void checkLightDiffuse() {
        // Creates the field and sets the center light to its maximum value
        LightFieldTest res = new LightFieldTest();
        res.testLightField[15][15][15] = 15;

        // Diffuses the light at center
        long startedAt = System.currentTimeMillis();
        LightDiffuser.diffuseLight(res, 15, 15, 15, LightType.BLOCK_LIGHT);
        out.println("Light diffusion algorithm took: " + (System.currentTimeMillis() - startedAt) + "ms");

        // Prints out the generated field on two different perspectives
        out.println();
        out.println(res.toXzPlaneString());
        out.println();
        out.println(res.toXyPlaneString());
    }

    @Test
    public void checkLightDrain() {
        // Creates the field and sets the center light to its maximum value
        LightFieldTest res = new LightFieldTest();
        res.testLightField[15][15][15] = 15;

        // Diffuses the light at center
        long startedAt = System.currentTimeMillis();
        LightDiffuser.diffuseLight(res, 15, 15, 15, LightType.BLOCK_LIGHT);
        out.println("Light diffusion algorithm took: " + (System.currentTimeMillis() - startedAt) + " ms");

        // Prints out the generated field after diffusing
        out.println();
        out.println(res.toXyPlaneString());

        // Removes the set light
        startedAt = System.currentTimeMillis();
        LightDiffuser.removeLight(res, 15, 15, 15, LightType.BLOCK_LIGHT);
        out.println("Light removal algorithm took: " + (System.currentTimeMillis() - startedAt) + " ms");

        // Prints out the generated field after removal
        out.println();
        out.println(res.toXyPlaneString());
    }

    private static class LightFieldTest implements LightDiffusionField {
        private static final int LF_WIDTH  = 32;
        private static final int LF_HEIGHT = 32;
        private static final int LF_LENGTH = 32;

        private int testLightField[][][];

        public LightFieldTest() {
            testLightField = new int[LF_WIDTH][LF_HEIGHT][LF_LENGTH];
        }

        @Override
        public int getLightAt(int x, int y, int z, LightType lightType) {
            return testLightField[x][y][z];
        }

        @Override
        public void setLightAt(int x, int y, int z, int lightLevel, LightType lightType) {
            testLightField[x][y][z] = lightLevel;
        }

        public String toXzPlaneString() {
            StringBuilder s = new StringBuilder();
            s.append("XZ Plane\n");
            for (int y = 0; y < LF_LENGTH; y++) {
                for (int x = 0; x < LF_WIDTH; x++) {
                    int l = testLightField[x][15][y];
                    if (l == 15)
                        s.append(ANSI_GREEN_BACKGROUND);
                    else if (l < 15 && l >= 11)
                        s.append(ANSI_RED_BACKGROUND);
                    else if (l <= 10 && l >= 6)
                        s.append(ANSI_YELLOW_BACKGROUND);
                    else if (l < 6 && l > 0)
                        s.append(ANSI_CYAN_BACKGROUND);
                    else
                        s.append(ANSI_RESET);
                    // Draws cell
                    s.append("[");
                    if (l / 10 == 0)
                        s.append("0");
                    s.append(l)
                            .append("]");
                }
                s.append("\n");
            }
            return s.toString();
        }

        public String toXyPlaneString() {
            StringBuilder s = new StringBuilder();
            s.append("XY Plane\n");
            for (int y = 0; y < LF_HEIGHT; y++) {
                for (int x = 0; x < LF_WIDTH; x++) {
                    int l = testLightField[x][y][15];
                    if (l == 15)
                        s.append(ANSI_GREEN_BACKGROUND);
                    else if (l < 15 && l >= 11)
                        s.append(ANSI_RED_BACKGROUND);
                    else if (l <= 10 && l >= 6)
                        s.append(ANSI_YELLOW_BACKGROUND);
                    else if (l < 6 && l > 0)
                        s.append(ANSI_CYAN_BACKGROUND);

                    s.append("[");
                    if (l / 10 == 0)
                        s.append("0");
                    s.append(l)
                            .append("]")
                            .append(ANSI_RESET);
                }
                s.append("\n");
            }
            return s.toString();
        }
    }
}
