#version 450

in vec3 TexCoords;

in float BlockLight;

uniform sampler2DArray image;

void main()
{
    gl_FragColor = (BlockLight + 0.1) * texture(image, TexCoords);
}