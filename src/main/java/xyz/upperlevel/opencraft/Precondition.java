package xyz.upperlevel.opencraft;

public class Precondition {

    private Precondition() {
    }

    public static void notNull(Object var, String varName) {
        if (var == null)
            throw new IllegalArgumentException(varName + " cannot be null.");
    }
}
