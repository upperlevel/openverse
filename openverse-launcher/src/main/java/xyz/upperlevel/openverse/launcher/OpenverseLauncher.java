package xyz.upperlevel.openverse.launcher;

public final class OpenverseLauncher {

    private OpenverseLauncher() {
    }

    public static void main(String[] args) {
        OpenverseSystem.getHandler().start();
    }
}
