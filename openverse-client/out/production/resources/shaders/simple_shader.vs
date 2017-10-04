#version 450

in vec3 position;
in vec3 texCoords;

uniform mat4 camera;

out vec3 TexCoords;

void main()
{
    gl_Position = camera * vec4(position, 1.0f);
    TexCoords = texCoords;
}