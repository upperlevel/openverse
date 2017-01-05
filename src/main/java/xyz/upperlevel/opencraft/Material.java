package xyz.upperlevel.opencraft;

import lombok.*;
import xyz.upperlevel.graphicengine.api.opengl.texture.Texture;

@RequiredArgsConstructor
@AllArgsConstructor
public class Material {

    @Getter @Setter private Texture texture;
    @Getter @Setter private Texture specularTexture;

    @Getter @Setter private float shininess;
}
