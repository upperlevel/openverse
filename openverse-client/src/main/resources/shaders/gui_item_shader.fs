#version 450

in vec3 TexCoords;

uniform sampler2DArray image;

void main()
{
    gl_FragColor = texture(image, TexCoords);
}
