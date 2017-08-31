#version 450

in vec4 Color;
in vec3 TexCoords;

out vec4 result;

uniform sampler2DArray texture;

void main()
{
    result = vec4(1, 0, 0, 0);// Color * texture(texture, TexCoords);
}