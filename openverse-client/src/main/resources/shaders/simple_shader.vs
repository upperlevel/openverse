#version 450

in vec3 position;
in vec3 texCoords;
in float blockLight;
in float blockSkylight;

uniform mat4 camera;

out vec3 TexCoords;
out float BlockLight;
out float BlockSkylight;

void main() {
    gl_Position = camera * vec4(position, 1.0f);
    TexCoords = texCoords;
    BlockLight = blockLight;
    BlockSkylight = blockSkylight;
}