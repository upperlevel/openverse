#version 410

// must be inside each shader
in vec2 textureCoords;

out vec4 outColor;
// ^^^
// custom
uniform vec4 color;

void main() {
    outColor = color;
}