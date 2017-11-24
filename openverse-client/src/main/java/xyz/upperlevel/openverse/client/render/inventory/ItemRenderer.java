package xyz.upperlevel.openverse.client.render.inventory;

import org.joml.Matrix4f;

public interface ItemRenderer {
    void renderInSlot(Matrix4f trans, SlotGui slot);

    void renderInHand(Matrix4f trans);

    //void renderDrop(Drop drop); TODO: drops
}
