#version 450

in vec3 TexCoords;

uniform sampler2DArray image;

void main()
{
    gl_FragColor = texture(image, vec3(TexCoords.x, 1 - TexCoords.y, TexCoords.z));
}
