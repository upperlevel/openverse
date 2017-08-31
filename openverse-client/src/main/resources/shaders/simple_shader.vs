#version 450

in vec3 position;
in vec4 color;
in vec3 texCoords;

uniform mat4 model;
uniform mat4 camera;

out vec4 Color;
out vec3 TexCoords;

void main()
{
    gl_Position = camera * model * vec4(pos, 1.0f);
    Color = color;
    TexCoords = texCoords;
}