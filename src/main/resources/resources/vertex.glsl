#version 410

// must be inside each shader
in vec3 position;
in vec2 inTextureCoords;

uniform mat4 model;
uniform mat4 camera;

out vec2 textureCoords;
// ^^^

void main() {
   gl_Position = camera * model * vec4(position, 1);
   textureCoords = inTextureCoords;
}