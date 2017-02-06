#version 330

// must be inside each shader
in vec3 position;
in vec4 inColor;
in vec2 inTextureCoords;

out vec4 color;
out vec2 textureCoords;

uniform mat4 camera;

void main()
{
   gl_Position = camera * vec4(position, 1f);
   color = inColor;
   textureCoords = inTextureCoords;
}