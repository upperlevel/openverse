#version 450

in vec4 Col;
in vec3 Tex_coord;

uniform vec4 uni_col;

out vec4 res;

uniform sampler2DArray tex;

void main()
{
    res = Col * texture(tex, Tex_coord);
}