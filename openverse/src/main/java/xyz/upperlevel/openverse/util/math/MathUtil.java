package xyz.upperlevel.openverse.util.math;

public final class MathUtil {

    public static int roundUp(int v, int interval) {
        if (v == 0) {
            return interval;
        }
        int i = v % interval;
        return i == 0 ? v : v + interval - i;
    }

    public static int floori(double v) {
        int i = (int) v;
        return v < i ? i - 1 : i;
    }

    public static long floorl(double v) {
        long i = (long) v;
        return v < i ? i - 1 : i;
    }

    public static int ceili(double v) {
        int i = (int) v;
        return v > i ? i + 1 : i;
    }

    public static long ceill(double v) {
        long i = (long) v;
        return v > i ? i + 1 : i;
    }

    public static int clamp(int v, int min, int max) {
        return v <= min ? min : v >= max ? max : v;
    }

    public static long clamp(long v, long min, long max) {
        return v <= min ? min : v >= max ? max : v;
    }

    public static float clamp(float v, float min, float max) {
        return v <= min ? min : v >= max ? max : v;
    }

    public static double clamp(double v, double min, double max) {
        return v <= min ? min : v >= max ? max : v;
    }

    public static float lerp(float a, float b, float perc) {
        return a * (1f - perc) + b * perc;
    }

    public static double lerp(double a, double b, float perc) {
        return a * (1f - perc) + b * perc;
    }

    public static double shortAngleDistance(float a, float b) {
        final int MAX_ANGLE = 360;
        float da = (b - a) % MAX_ANGLE;
        return 2*da % MAX_ANGLE - da;
    }

    public static double shortAngleDistance(double a, double b) {
        final int MAX_ANGLE = 360;
        double da = (b - a) % MAX_ANGLE;
        return 2*da % MAX_ANGLE - da;
    }

    public static double lerpAngle(float a, float b, float perc) {
        return a + shortAngleDistance(a, b) * perc;
    }

    public static double lerpAngle(double a, double b, double perc) {
        return a + shortAngleDistance(a, b) * perc;
    }


    private MathUtil() {}
}
