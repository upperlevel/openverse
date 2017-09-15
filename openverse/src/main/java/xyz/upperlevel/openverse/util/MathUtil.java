package xyz.upperlevel.openverse.util;

public final class MathUtil {

    public static int roundUp(int x, int interval) {
        int i = x % interval;
        return i == 0 ? interval : x + interval - i;
    }

    private MathUtil() {}
}
