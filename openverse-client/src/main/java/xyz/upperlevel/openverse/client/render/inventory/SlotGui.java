package xyz.upperlevel.openverse.client.render.inventory;

import lombok.Getter;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.inventory.PlayerInventorySession.InteractAction;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.network.inventory.PlayerInventoryActionPacket;
import xyz.upperlevel.ulge.gui.Gui;
import xyz.upperlevel.ulge.gui.GuiRenderer;
import xyz.upperlevel.ulge.opengl.texture.Texture2d;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.ulge.window.event.button.MouseButton;

import static xyz.upperlevel.openverse.inventory.PlayerInventorySession.InteractAction.*;

public class SlotGui extends Gui {
    @Getter
    private final Slot handle;

    public SlotGui(Slot handle) {
        this.handle = handle;
    }

    @Override
    public void reloadLayout(int pX, int pY, int pW, int pH) {
        super.reloadLayout(pX, pY, pW, pH);
    }

    @Override
    public void renderCurrent() {
        ItemType type = handle.getContent().getType(OpenverseClient.get().getResources().itemTypes());
        if (type == ItemType.AIR) return;
        {
            GuiRenderer r = GuiRenderer.get();
            r.setColor(Color.RED);
            r.setTexture(Texture2d.NULL);
            r.render(getWindow(), getBounds());
        }
        //TODO: add cache (using inventory listener)
        ItemRenderer renderer = OpenverseClient.get().getItemRendererRegistry().get(type);
        if (renderer == null) {
            OpenverseClient.get().getLogger().warning("Cannot find renderer for type: " + type);
            return;
        }
        renderer.renderInSlot(handle.getContent(), getWindow(), getBounds(), this);
    }

    @Override
    public void onClickBegin(double x, double y, MouseButton button) {
        InteractAction action;
        switch (button) {
            case LEFT:
                action = OpenverseClient.get().isShifting() ? SHIFT_LEFT_CLICK : LEFT_CLICK;
                break;
            case RIGHT:
                action = OpenverseClient.get().isShifting() ? SHIFT_RIGHT_CLICK : RIGHT_CLICK;
                break;
            default:
                action = null;
                break;
        }
        if (action != null) {
            OpenverseClient client = OpenverseClient.get();
            client.getPlayer().getInventorySession().onInteract(handle, action);
            client.getEndpoint().getConnection().send(client.getChannel(), new PlayerInventoryActionPacket(action, handle.getId()));
        }
        super.onClickBegin(x, y, button);
    }
}
