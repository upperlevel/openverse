package xyz.upperlevel.openverse.client.render.inventory;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.ulge.gui.BaseGui;
import xyz.upperlevel.ulge.gui.Gui;

@RequiredArgsConstructor
public abstract class InventoryGui<I extends Inventory> extends BaseGui {
    private final I handle;
}
