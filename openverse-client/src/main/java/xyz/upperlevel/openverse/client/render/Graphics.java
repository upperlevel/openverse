package xyz.upperlevel.openverse.client.render;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.event.WorldChangeEvent;

public class Graphics {

    private static RenderWorld world; // system to render the world
    private static final ShapeCompilerManager shapeCompilerManager = new ShapeCompilerManager();
    private static final TextureBakery textureBakery = new TextureBakery();

    static {
        Openverse.getEventManager().register(new EventListener());
    }

    public static RenderWorld getRenderWorld() {
        return world;
    }

    public static ShapeCompilerManager getShapeCompilerManager() {
        return shapeCompilerManager;
    }

    public static TextureBakery getTextureBakery() {
        return textureBakery;
    }

    public static class EventListener implements Listener {
        @EventHandler(priority = EventPriority.HIGHEST)
        public void onWorldChange(WorldChangeEvent event) {
            if(!event.isCancelled())
                world = new RenderWorld(event.getWorld());
        }
    }
}
