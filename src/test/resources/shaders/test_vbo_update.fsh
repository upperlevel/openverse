#version 330

in vec4 color;
in vec2 textureCoords;

uniform sampler2D text;

void main()
{
    gl_FragColor = texture(text, textureCoords) * color;
}