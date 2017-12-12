package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Vector2d;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.inventory.PlayerInventorySession;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.launcher.OpenverseLauncher;
import xyz.upperlevel.openverse.world.entity.player.events.PlayerInventoryCloseEvent;
import xyz.upperlevel.openverse.world.entity.player.events.PlayerInventoryOpenEvent;
import xyz.upperlevel.ulge.gui.GuiBounds;
import xyz.upperlevel.ulge.gui.GuiViewer;
import xyz.upperlevel.ulge.window.Window;

import static org.lwjgl.opengl.GL11.*;

public class GuiManager implements Listener {
    private GuiViewer viewer = new GuiViewer(OpenverseLauncher.get().getGame().getWindow());
    private HandSlotGui handSlotGui;

    public GuiManager() {
        Openverse.getEventManager().register(this);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerOpenInventory(PlayerInventoryOpenEvent event) {
        //Should never happen but you never know
        if (event.getPlayer() != OpenverseClient.get().getPlayer()) return;
        OpenverseClient.get().setCaptureInput(false);
        InventoryGui<?> currentGui = OpenverseClient.get().getInventoryGuiRegistry().create(event.getInventory());
        viewer.open(currentGui);
        currentGui.reloadLayout();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCloseInventory(PlayerInventoryCloseEvent event) {
        //Should never happen but you never know
        if (event.getPlayer() != OpenverseClient.get().getPlayer()) return;
        handSlotGui = null;
        viewer.close();
        OpenverseClient.get().setCaptureInput(true);
    }

    public void render(float partialTicks) {
        if (!viewer.getHandle().isEmpty()) {
            glClear(GL_DEPTH_BUFFER_BIT);
            glDisable(GL_CULL_FACE);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            viewer.render();

            PlayerInventorySession session = OpenverseClient.get()
                    .getPlayer()
                    .getInventorySession();
            if (session != null) {
                if (handSlotGui == null) {
                    handSlotGui = new  HandSlotGui(session.getHand().get());
                }
                ItemStack item = session
                        .getHand()
                        .get()
                        .getContent();
                if (!item.isEmpty()) {
                    Window window = viewer.getCurrent().getWindow();
                    Vector2d mousePos = window.getCursorPosition();
                    ItemRenderer renderer = OpenverseClient.get().getItemRendererRegistry().get(item.getType());
                    int size = Math.min(window.getWidth() / 10, window.getHeight() / 10);
                    renderer.renderInSlot(window, new GuiBounds(mousePos.x - size/2, mousePos.y - size/2, mousePos.x + size, mousePos.y + size), handSlotGui);
                }
            } else Openverse.logger().warning("Null session!");

            glDisable(GL_BLEND);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_CULL_FACE);
        }
    }

    protected static class HandSlotGui extends SlotGui {
        public HandSlotGui(Slot handle) {
            super(handle);
        }
    }
}
