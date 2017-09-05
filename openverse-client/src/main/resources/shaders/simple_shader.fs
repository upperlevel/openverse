#version 450

in vec4 Color;
in vec3 TexCoords;

uniform sampler2DArray image;

void main()
{
    gl_FragColor = Color + texture(image, TexCoords) * 0; // no textures atm
}