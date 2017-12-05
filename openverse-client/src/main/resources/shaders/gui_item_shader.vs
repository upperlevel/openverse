#version 450

in vec3 position;
in vec3 texCoords;

uniform vec4 bounds;

out vec3 TexCoords;

void main()
{
    // (coords + size * position) * 2 - 1
    // in 0, 0 only coords (minX, minY), in 1, 1 coords + size (maxX, maxY)...
    gl_Position = vec4((bounds.xy + bounds.zw * position.xy) * vec2(2) - vec2(1), 0.0, 1.0);
    TexCoords = texCoords;
}
