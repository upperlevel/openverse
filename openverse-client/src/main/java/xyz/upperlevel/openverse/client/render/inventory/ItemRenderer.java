package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;
import xyz.upperlevel.ulge.gui.GuiBounds;
import xyz.upperlevel.ulge.window.Window;

public interface ItemRenderer {
    void renderInSlot(Window window, GuiBounds trans, SlotGui slot);

    void renderInHand(Matrix4f trans);

    //void renderDrop(Drop drop); TODO: drops
}
