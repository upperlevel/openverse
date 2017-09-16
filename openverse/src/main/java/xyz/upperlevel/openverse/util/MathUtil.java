package xyz.upperlevel.openverse.util;

public final class MathUtil {

    public static int roundUp(int x, int interval) {
        if (x == 0) {
            return interval;
        }
        int i = x % interval;
        return i == 0 ? x : x + interval - i;
    }

    private MathUtil() {}
}
