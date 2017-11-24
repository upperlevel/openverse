package xyz.upperlevel.openverse.client.render.inventory;

import lombok.Getter;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.ulge.gui.Gui;
import xyz.upperlevel.ulge.gui.GuiRenderer;

public class SlotGui extends Gui {
    @Getter
    private final Slot handle;

    public SlotGui(Slot handle) {
        this.handle = handle;
    }

    @Override
    public void render(Matrix4f trans, GuiRenderer render) {
        super.render(trans, render);
        ItemType type = handle.getContent().getType();
        if (type == ItemType.AIR) return;
        //TODO: add cache (using inventory listener)
        ItemRenderer renderer = OpenverseClient.get().getItemRendererRegistry().get(type);
        if (renderer == null) {
            Openverse.logger().warning("Cannot find renderer for type: " + type);
            return;
        }
        renderer.renderInSlot(trans, this);
    }
}
