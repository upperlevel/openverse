package xyz.upperlevel.openverse.client.render.inventory;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.ulge.gui.BaseGui;
import xyz.upperlevel.ulge.gui.GuiBounds;

public class SlotGui extends BaseGui {
    @Getter
    private final Slot handle;

    public SlotGui(Slot handle) {
        this.handle = handle;
    }

    @Override
    public void render(GuiBounds parentBounds) {
        GuiBounds bounds = parentBounds.insideRelative(getBounds());
        ItemType type = handle.getContent().getType();
        if (type == ItemType.AIR) return;
        //TODO: add cache (using inventory listener)
        ItemRenderer renderer = OpenverseClient.get().getItemRendererRegistry().get(type);
        if (renderer == null) {
            Openverse.logger().warning("Cannot find renderer for type: " + type);
            return;
        }
        renderer.renderInSlot(bounds, this);
    }
}
