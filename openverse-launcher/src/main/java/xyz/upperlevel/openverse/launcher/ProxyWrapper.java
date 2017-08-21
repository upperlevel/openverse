package xyz.upperlevel.openverse.launcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyWrapper {
    private final Object handle;

    public void loadResources() {
        try {
            Object resManager = handle.getClass().getMethod("getResourceManager").invoke(handle);
            resManager.getClass().getMethod("load").invoke(resManager);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
