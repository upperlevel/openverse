#version 450

in vec3 position;
in vec3 texCoords;
in float blockSkylight;

uniform mat4 camera;

out vec3 TexCoords;
out float BlockSkylight;

void main()
{
    gl_Position = camera * vec4(position, 1.0f);
    TexCoords = texCoords;
    BlockSkylight = blockSkylight;
}