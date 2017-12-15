#version 450

in vec3 TexCoords;
in float BlockLight;
in float BlockSkylight;

uniform sampler2DArray image;
uniform float worldSkylight;

void main() {
    // Todo: fix temporary ()
    gl_FragColor = ((BlockSkylight * worldSkylight) + 1) * (BlockLight + 0.1) * texture(image, TexCoords);
}