#version 450

in vec4 Color;
in vec3 TexCoords;

out vec4 result;

uniform sampler2DArray texture;

void main()
{
    result = Color * texture(texture, TexCoords);
}