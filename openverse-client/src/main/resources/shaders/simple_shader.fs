#version 450

in vec3 TexCoords;
in float BlockLight;
in float BlockSkylight;

uniform sampler2DArray image;
uniform float worldSkylight;

void main() {
    gl_FragColor = min((BlockSkylight * worldSkylight) + (BlockLight + 0.1), 1) * texture(image, TexCoords);
}