package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.ulge.gui.GuiBounds;
import xyz.upperlevel.ulge.window.Window;

public interface ItemRenderer {
    void renderInSlot(ItemStack item, Window window, GuiBounds trans, SlotGui slot);

    void renderInHand(ItemStack item, Matrix4f trans);

    //void renderDrop(Drop drop); TODO: drops
}
