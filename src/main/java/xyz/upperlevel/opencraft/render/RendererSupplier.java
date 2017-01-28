package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
public class RendererSupplier<R extends Renderer> {

    @Getter
    @Setter
    @NonNull
    private R renderer;
}