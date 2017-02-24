#version 330

in vec4 Col;
in vec2 Tex_coord;

out vec4 res;

uniform sampler2D tex;

void main()
{
    res = Col * texture(tex, Tex_coord);
}