#version 450

in vec3 position;
in vec3 texCoords;

uniform vec4 bounds;

out vec3 TexCoords;

void main()
{
    gl_Position = vec4(bounds.xy + bounds.wz * position.xy, position.z, 1.0f);
    TexCoords = texCoords;
}
