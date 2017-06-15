package xyz.upperlevel.openverse.client.render.event;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.event.CancellableEvent;
import xyz.upperlevel.openverse.client.render.RenderOptions;

public class RenderOptionsChangeEvent extends CancellableEvent {
    @Getter
    @Setter
    private RenderOptions options;

    @Getter
    private final RenderOptions oldOptions = RenderOptions.get();

    public RenderOptionsChangeEvent(RenderOptions options) {
        this.options = options;
    }
}
