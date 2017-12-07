package xyz.upperlevel.openverse.client.render.inventory;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.ulge.gui.Gui;
import xyz.upperlevel.ulge.gui.GuiRenderer;
import xyz.upperlevel.ulge.opengl.texture.Texture2d;
import xyz.upperlevel.ulge.util.Color;

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
        ItemType type = handle.getContent().getType();
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
            Openverse.logger().warning("Cannot find renderer for type: " + type);
            return;
        }
        renderer.renderInSlot(getWindow(), getBounds(), this);
    }
}
