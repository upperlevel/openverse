package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.world.entity.player.events.PlayerInventoryCloseEvent;
import xyz.upperlevel.openverse.world.entity.player.events.PlayerInventoryOpenEvent;
import xyz.upperlevel.ulge.gui.Gui;
import xyz.upperlevel.ulge.gui.GuiRenderer;
import xyz.upperlevel.ulge.gui.SingleGuiViewer;
import xyz.upperlevel.ulge.gui.impl.GuiPane;
import xyz.upperlevel.ulge.util.Color;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GuiManager implements Listener {
    private SingleGuiViewer viewer = new SingleGuiViewer();
    private GuiRenderer renderer = new GuiRenderer();

    public GuiManager() {
        Openverse.getEventManager().register(this);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerOpenInventory(PlayerInventoryOpenEvent event) {
        //Should never happen but you never know
        if (event.getPlayer() != OpenverseClient.get().getPlayer()) return;
        InventoryGui<?> currentGui = OpenverseClient.get().getInventoryGuiRegistry().create(event.getInventory());
        viewer.open(currentGui);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCloseInventory(PlayerInventoryCloseEvent event) {
        //Should never happen but you never know
        if (event.getPlayer() != OpenverseClient.get().getPlayer()) return;
        viewer.close();
    }

    public void render(float partialTicks) {
        Gui gui = viewer.get();
        if (gui != null) {
            renderer.getProgram().bind();
            gui.render(new Matrix4f(), renderer);
        }
    }

    public static class CustomGuiPane extends GuiPane {

        @Override
        public void render(Matrix4f transformation, GuiRenderer renderer) {
            if (isClicked()) {
                setColor(Color.RED);
            } else if (isHover()) {
                setColor(Color.YELLOW);
            } else {
                setColor(Color.GREEN);
            }
            super.render(transformation, renderer);
        }
    }
}
