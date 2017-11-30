package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;
import xyz.upperlevel.ulge.gui.GuiBounds;

public interface ItemRenderer {
    void renderInSlot(GuiBounds trans, SlotGui slot);

    void renderInHand(Matrix4f trans);

    //void renderDrop(Drop drop); TODO: drops
}
