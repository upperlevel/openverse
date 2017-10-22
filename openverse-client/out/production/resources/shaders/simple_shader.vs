#version 450

in vec3 position;
in vec3 texCoords;

// the intensity of the light on this block (from 0.0 to 1.0)
in float blockLight;

uniform mat4 camera;

out vec3 TexCoords;
out float BlockLight;

void main()
{
    gl_Position = camera * vec4(position, 1.0f);
    TexCoords = texCoords;
    BlockLight = blockLight;
}