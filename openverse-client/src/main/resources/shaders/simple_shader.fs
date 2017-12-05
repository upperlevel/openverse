#version 450

in vec3 TexCoords;
in float BlockSkylight;

uniform sampler2DArray image;
uniform float worldSkylight;

void main()
{
    gl_FragColor = ((BlockSkylight + 1) * worldSkylight) * texture(image, TexCoords);
}